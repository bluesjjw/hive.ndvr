package cn.pku.net.db.hive.ndvr.client;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import com.google.gson.Gson;

import cn.pku.net.db.hive.ndvr.util.MyThreadPool;
import cn.pku.net.db.hive.ndvr.entity.HSVSigEntity;
import cn.pku.net.db.hive.ndvr.entity.VideoHSVSigEntity;
import cn.pku.net.db.hive.ndvr.util.GlobalSigGenerator;
import cn.pku.net.db.hive.ndvr.entity.KeyFrameEntity;
import cn.pku.net.db.hive.ndvr.entity.GlobalSimilarVideo;
import cn.pku.net.db.hive.ndvr.entity.TextSimilarVideo;
import cn.pku.net.db.hive.ndvr.common.Const;

import cn.pku.net.db.hive.ndvr.entity.VideoInfoEntity;

public class TextGlobalRetrieval implements Runnable {

  private static final Logger logger = Logger
      .getLogger(TextGlobalRetrieval.class);
  private String queryVideoId;
  private static String HIVE_SERVER_HOST = "162.105.146.209";
  private static int HIVE_SERVER_PORT = 10000;
  private static String driverName = "org.apache.hive.jdbc.HiveDriver";

  public TextGlobalRetrieval(String id) {
    this.queryVideoId = id;
  }

  @Override
  public void run() {
    long startTime = System.currentTimeMillis();
    try {
      Class.forName(driverName);
    } catch (ClassNotFoundException e) {
      logger.error("Class not found: " + driverName + e);
    }
    VideoInfoEntity queryVideo = null;
    // duration筛选的候选video
    List<VideoInfoEntity> candidates = new ArrayList<VideoInfoEntity>();
    // text相似的video
    List<TextSimilarVideo> textSimilarVideoList = new ArrayList<TextSimilarVideo>();
    // global visual feature相似的video
    List<GlobalSimilarVideo> globalSimilarVideoList = new ArrayList<GlobalSimilarVideo>();
    // 输出的videoId list
    List<String> results = new ArrayList<String>();
    try {

      Connection con = DriverManager.getConnection(
          "jdbc:hive2://" + HIVE_SERVER_HOST + ":" + HIVE_SERVER_PORT + "/ndvr",
          "hive", "hive");
      Statement stmt = con.createStatement();
      String queryStr = "select * from videoinfo where videoid="
          + this.queryVideoId;
      ResultSet res = stmt.executeQuery(queryStr);
      if (res.next()) {
        queryVideo = new VideoInfoEntity();
        queryVideo.setVideoId(res.getString(1));
        queryVideo.setTopicId(res.getString(2));
        queryVideo.setSource(res.getString(3));
        queryVideo.setVideoFileName(res.getString(4));
        queryVideo.setDuration(res.getInt(5));
        queryVideo.setFormat(res.getString(6));
        queryVideo.setTitle(res.getString(7));
        queryVideo.setUrl(res.getString(8));
      }
      if (null != queryVideo) {
        int minQueryDuration = queryVideo.getDuration()
            - Const.STORM_CONFIG.VIDEO_DURATION_WINDOW;
        if (minQueryDuration <= 0) {
          minQueryDuration = 1;
        }
        int maxQueryDuration = queryVideo.getDuration()
            + Const.STORM_CONFIG.VIDEO_DURATION_WINDOW;
        queryStr = "select * from videoinfo where duration>" + minQueryDuration
            + " and duration<" + maxQueryDuration;
        res = stmt.executeQuery(queryStr);
        while (res.next()) {
          VideoInfoEntity ent = new VideoInfoEntity();
          ent.setVideoId(res.getString(1));
          ent.setTopicId(res.getString(2));
          ent.setSource(res.getString(3));
          ent.setVideoFileName(res.getString(4));
          ent.setDuration(res.getInt(5));
          ent.setFormat(res.getString(6));
          ent.setTitle(res.getString(7));
          ent.setUrl(res.getString(8));
          candidates.add(ent);
          // logger.info(ent.toString());
        }
      } else {
        logger.info("Query video not exists: " + this.queryVideoId + ", cost "
            + (System.currentTimeMillis() - startTime) + " ms");
        return;
      }
      if (!candidates.isEmpty()) {
        logger.info(
            "Size of candidantes with similar duration: " + candidates.size());
        List<String> queryTextSplits = getSplitText(queryVideo.getTitle());
        logger.info("Query video title's splits: "
            + (new Gson()).toJson(queryTextSplits));
        if (queryTextSplits.isEmpty()) {
          logger.info("Query video's title is invalid, videoId: "
              + this.queryVideoId + ", cost "
              + (System.currentTimeMillis() - startTime) + " ms");
          return;
        }
        // 计算text近似的视频
        for (VideoInfoEntity comparedVideo : candidates) {
          List<String> comparedTextSplits = getSplitText(
              comparedVideo.getTitle());
          // logger.info("Compared video title's splits: "
          // + (new Gson()).toJson(comparedTextSplits));
          if (!comparedTextSplits.isEmpty()) {
            float queryVScompared = (float) 0.0; // query与compare逐词比较的相似度
            float comparedVSquery = (float) 0.0; // compare与query逐词比较的相似度
            int sameTermNum = 0;
            // 计算query与compare相同的term数量占query总term的比例
            for (int i = 0; i < queryTextSplits.size(); i++) {
              int minIndex = (i - Const.STORM_CONFIG.TEXT_COMPARED_WINDOW) >= 0
                  ? (i - Const.STORM_CONFIG.TEXT_COMPARED_WINDOW) : 0;
              int maxIndex = (i
                  + Const.STORM_CONFIG.TEXT_COMPARED_WINDOW) < comparedTextSplits
                      .size() ? (i + Const.STORM_CONFIG.TEXT_COMPARED_WINDOW)
                          : (comparedTextSplits.size() - 1);
              for (int j = minIndex; j < maxIndex + 1; j++) {
                if (queryTextSplits.get(i).equals(comparedTextSplits.get(j))) {
                  sameTermNum++;
                  break;
                }
              }
            }
            queryVScompared = sameTermNum / (float) queryTextSplits.size();

            // 计算compare与query相同的term数量占compare总term的比例
            sameTermNum = 0;
            for (int i = 0; i < comparedTextSplits.size(); i++) {
              int minIndex = (i - Const.STORM_CONFIG.TEXT_COMPARED_WINDOW) >= 0
                  ? (i - Const.STORM_CONFIG.TEXT_COMPARED_WINDOW) : 0;
              int maxIndex = (i
                  + Const.STORM_CONFIG.TEXT_COMPARED_WINDOW) < queryTextSplits
                      .size() ? (i + Const.STORM_CONFIG.TEXT_COMPARED_WINDOW)
                          : (queryTextSplits.size() - 1);
              for (int j = minIndex; j < maxIndex + 1; j++) {
                if (comparedTextSplits.get(i).equals(queryTextSplits.get(j))) {
                  sameTermNum++;
                  break;
                }
              }
            }
            comparedVSquery = sameTermNum / (float) comparedTextSplits.size();

            if (comparedVSquery == 0 || queryVScompared == 0) {
              continue;
            }

            // 调和相似度
            float harmonicSimilarity = queryVScompared * comparedVSquery
                / (queryVScompared + comparedVSquery);
            // logger.info(
            // "Similarity of video " + queryVideo.getVideoId() + " and video "
            // + comparedVideo.getVideoId() + " :" + queryVScompared + ", "
            // + comparedVSquery + ", " + harmonicSimilarity);
            // 如果相似度大于阈值,存入相似列表
            if (harmonicSimilarity >= Const.STORM_CONFIG.TEXT_SIMILARITY_THRESHOLD) {
              TextSimilarVideo textSimilarVideo = new TextSimilarVideo(
                  comparedVideo.getVideoId(), harmonicSimilarity);
              textSimilarVideoList.add(textSimilarVideo);
            }
          }
        }
      }
      // 计算global visual feature相似的视频
      if (!textSimilarVideoList.isEmpty()) {
        logger.info("Text similar video size: " + textSimilarVideoList.size()
            + ", text similar video: "
            + (new Gson()).toJson(textSimilarVideoList));
        // global feature
        queryStr = "select * from keyframe where videoid=" + this.queryVideoId;
        res = stmt.executeQuery(queryStr);
        List<KeyFrameEntity> keyframeList = new ArrayList<KeyFrameEntity>();
        while (res.next()) {
          KeyFrameEntity ent = new KeyFrameEntity();
          ent.setVideoId(res.getString(1));
          ent.setKeyFrameName(res.getString(2));
          ent.setVideoFileName(res.getString(3));
          ent.setSerialId(res.getString(4));
          keyframeList.add(ent);
        }
        if (!keyframeList.isEmpty()) {
          Collections.sort(keyframeList, new KeyFrameEntity());
          HSVSigEntity queryHsvSignature = GlobalSigGenerator
              .generate(keyframeList);
          VideoHSVSigEntity queryVideoHsvSig = new VideoHSVSigEntity(
              queryVideo.getVideoId(), queryHsvSignature);
          if (null != queryVideoHsvSig) {
            for (TextSimilarVideo similarVideo : textSimilarVideoList) {
              VideoHSVSigEntity comparedVideoHsvSig = null;
              queryStr = "select * from hsvsignature where videoid="
                  + similarVideo.getVideoId();
              res = stmt.executeQuery(queryStr);
              if (res.next()) {
                String sigStr = res.getString(2);
                HSVSigEntity sigEnt = (new Gson()).fromJson(sigStr,
                    HSVSigEntity.class);
                comparedVideoHsvSig = new VideoHSVSigEntity(
                    similarVideo.getVideoId(), sigEnt);
              }
              if (null != comparedVideoHsvSig) {
                float euclideanDistance = getEuclideanDistance(
                    queryVideoHsvSig.getSig(), comparedVideoHsvSig.getSig());
                if (euclideanDistance <= Const.STORM_CONFIG.GLOBALSIG_EUCLIDEAN_THRESHOLD) {
                  globalSimilarVideoList.add(new GlobalSimilarVideo(
                      similarVideo.getVideoId(), euclideanDistance));
                }
              }
            }
          }
        }
      }
      if (!globalSimilarVideoList.isEmpty()) {
        Collections.sort(globalSimilarVideoList, new GlobalSimilarVideo());
        String globalSimVideoListStr = (new Gson())
            .toJson(globalSimilarVideoList);
        logger
            .info("Global similar video size: " + globalSimilarVideoList.size()
                + ", global similar video: " + globalSimVideoListStr + ", cost "
                + (System.currentTimeMillis() - startTime) + " ms");
      } else {
        logger.info(
            "There is no similar video for video: " + queryVideo.getVideoId()
                + ", cost " + (System.currentTimeMillis() - startTime) + " ms");
      }
    } catch (SQLException e) {
      logger.error("Hive SQL eroor." + e);
    }
  }

  public static List<String> getSplitText(String text) {
    List<String> splitText = new ArrayList<String>();
    StringReader sr = new StringReader(text);
    IKSegmenter ik = new IKSegmenter(sr, true);
    Lexeme lex = null;
    try {
      while ((lex = ik.next()) != null) {
        splitText.add(lex.getLexemeText());
      }
    } catch (IOException e) {
      logger.error("IO error when use IKanalyzer. ", e);
    }
    return splitText;
  }

  public static float getEuclideanDistance(HSVSigEntity queryHSVSig,
      HSVSigEntity comparedHSVSig) {
    if (null == queryHSVSig || null == comparedHSVSig) {
      return (float) 100.0;
    }
    float distance = (float) 0.0;
    distance = (float) Math
        .sqrt(Math.pow(queryHSVSig.getBin1() - comparedHSVSig.getBin1(), 2)
            + Math.pow(queryHSVSig.getBin2() - comparedHSVSig.getBin2(), 2)
            + Math.pow(queryHSVSig.getBin3() - comparedHSVSig.getBin3(), 2)
            + Math.pow(queryHSVSig.getBin3() - comparedHSVSig.getBin3(), 2)
            + Math.pow(queryHSVSig.getBin4() - comparedHSVSig.getBin4(), 2)
            + Math.pow(queryHSVSig.getBin5() - comparedHSVSig.getBin5(), 2)
            + Math.pow(queryHSVSig.getBin6() - comparedHSVSig.getBin6(), 2)
            + Math.pow(queryHSVSig.getBin7() - comparedHSVSig.getBin7(), 2)
            + Math.pow(queryHSVSig.getBin8() - comparedHSVSig.getBin8(), 2)
            + Math.pow(queryHSVSig.getBin9() - comparedHSVSig.getBin9(), 2)
            + Math.pow(queryHSVSig.getBin10() - comparedHSVSig.getBin10(), 2)
            + Math.pow(queryHSVSig.getBin11() - comparedHSVSig.getBin11(), 2)
            + Math.pow(queryHSVSig.getBin12() - comparedHSVSig.getBin12(), 2)
            + Math.pow(queryHSVSig.getBin13() - comparedHSVSig.getBin13(), 2)
            + Math.pow(queryHSVSig.getBin14() - comparedHSVSig.getBin14(), 2)
            + Math.pow(queryHSVSig.getBin15() - comparedHSVSig.getBin15(), 2)
            + Math.pow(queryHSVSig.getBin16() - comparedHSVSig.getBin16(), 2)
            + Math.pow(queryHSVSig.getBin17() - comparedHSVSig.getBin17(), 2)
            + Math.pow(queryHSVSig.getBin18() - comparedHSVSig.getBin18(), 2)
            + Math.pow(queryHSVSig.getBin19() - comparedHSVSig.getBin19(), 2)
            + Math.pow(queryHSVSig.getBin20() - comparedHSVSig.getBin20(), 2)
            + Math.pow(queryHSVSig.getBin21() - comparedHSVSig.getBin21(), 2)
            + Math.pow(queryHSVSig.getBin22() - comparedHSVSig.getBin22(), 2)
            + Math.pow(queryHSVSig.getBin23() - comparedHSVSig.getBin23(), 2)
            + Math.pow(queryHSVSig.getBin23() - comparedHSVSig.getBin23(), 2)
            + Math.pow(queryHSVSig.getBin24() - comparedHSVSig.getBin24(), 2));
    return distance;
  }

  public static void main(String[] args) {
    for (int i = 0; i < 1; i++) {
      ExecutorService pool = MyThreadPool.getPool();
      TextGlobalRetrieval t = new TextGlobalRetrieval("1");
      pool.submit(t);
    }
    // List<String> splits = getSplitText("今天是星期一");
    // logger.info((new Gson()).toJson(splits));
  }
}
