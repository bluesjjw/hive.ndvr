package cn.pku.net.db.hive.ndvr.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import cn.pku.net.db.hive.ndvr.common.Const;
import cn.pku.net.db.hive.ndvr.entity.HSVSigEntity;
import cn.pku.net.db.hive.ndvr.entity.VideoInfoEntity;
import cn.pku.net.db.hive.ndvr.util.FeatureSimilarity;
import cn.pku.net.db.hive.ndvr.util.MyThreadPool;

public class TextGlobalDetection implements Runnable {

  private static final Logger logger = Logger
      .getLogger(TextGlobalDetection.class);
  private static String HIVE_SERVER_HOST = "162.105.146.209";
  private static int HIVE_SERVER_PORT = 10000;
  private static String driverName = "org.apache.hive.jdbc.HiveDriver";

  private List<String> videoIdList = null;

  public TextGlobalDetection(List<String> list) {
    this.videoIdList = list;
  }

  @Override
  public void run() {
    long startTime = System.currentTimeMillis();
    // decection videos的元数据
    List<VideoInfoEntity> videoInfoList = new ArrayList<VideoInfoEntity>();
    // 比较text的出的结果
    Set<String> textResult = new HashSet<String>();
    // 比较global visual feature的出的结果
    Set<String> globalResult = new HashSet<String>();
    try {
      Class.forName(driverName);
    } catch (ClassNotFoundException e) {
      logger.error("Class not found: " + driverName + e);
    }
    try {
      Connection con = DriverManager.getConnection(
          "jdbc:hive2://" + HIVE_SERVER_HOST + ":" + HIVE_SERVER_PORT + "/ndvr",
          "hive", "hive");
      Statement stmt = con.createStatement();
      String queryStr = "select * from videoinfo where videoid in ";
      StringBuilder videoIdListStr = new StringBuilder("(");
      for (int i = 0; i < videoIdList.size(); i++) {
        videoIdListStr.append(videoIdList.get(i));
        if (i < videoIdList.size() - 1) {
          videoIdListStr.append(",");
        }
      }
      videoIdListStr.append(")");
      queryStr += videoIdListStr.toString();
      logger.info("Query: " + queryStr);
      // 取出detection task对应的视频id
      ResultSet res = stmt.executeQuery(queryStr);
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
        videoInfoList.add(ent);
      }
      // logger.info(new Gson().toJson(videoInfoList));
      if (!videoInfoList.isEmpty()) {
        for (int i = videoInfoList.size() - 1; i >= 0; i--) {
          boolean isNovel = true;
          for (int j = i - 1; j >= 0; j--) {
            VideoInfoEntity ent1 = videoInfoList.get(i);
            VideoInfoEntity ent2 = videoInfoList.get(j);
            float textSimilarity = FeatureSimilarity.getTextSim(ent1.getTitle(),
                ent2.getTitle());
            // 找到一对相似的视频对
            if (textSimilarity >= Const.STORM_CONFIG.TEXT_SIMILARITY_THRESHOLD) {
              logger.info("Video " + videoInfoList.get(i).getVideoId()
                  + " is textual duplicate, similarity: " + textSimilarity);
              isNovel = false;
              break;
            }
          }
          if (isNovel) {
            textResult.add(videoInfoList.get(i).getVideoId());
          }
        }
        logger.info("Text similar video list size: " + textResult.size()
            + ", video list: " + (new Gson()).toJson(textResult) + ", cost "
            + (System.currentTimeMillis() - startTime) + " ms");
        // 视频list的HSV标签
        List<HSVSigEntity> hsvSigList = new ArrayList<HSVSigEntity>();
        for (VideoInfoEntity ent : videoInfoList) {
          queryStr = "select * from hsvsignature where videoid="
              + ent.getVideoId();
          res = stmt.executeQuery(queryStr);
          if (res.next()) {
            String sigStr = res.getString(2);
            HSVSigEntity sigEnt = (new Gson()).fromJson(sigStr,
                HSVSigEntity.class);
            hsvSigList.add(sigEnt);
          } else {
            hsvSigList.add(null);
          }
        }
        // logger.info("HSV signature list: " + (new
        // Gson()).toJson(hsvSigList));
        // global visual相似的视频
        if (!hsvSigList.isEmpty()) {
          for (int i = videoInfoList.size() - 1; i >= 0; i--) {
            boolean isNovel = true;
            HSVSigEntity sigEnt1 = hsvSigList.get(i);
            if(null == sigEnt1){
              isNovel = false;
              continue;
            }
            for (int j = i - 1; j >= 0; j--) {
              HSVSigEntity sigEnt2 = hsvSigList.get(j);
              if (null == sigEnt2) {
                continue;
              }
              float euclideanDistance = FeatureSimilarity
                  .getGlobalEuclideanDistance(sigEnt1, sigEnt2);
              // logger.info("euclidean distance: " + euclideanDistance);
              // 找到一对相似的视频对
              if (euclideanDistance <= Const.STORM_CONFIG.GLOBALSIG_EUCLIDEAN_THRESHOLD) {
                logger.info("Video " + videoInfoList.get(i).getVideoId()
                    + " is global visual duplicate, euclidean distance: "
                    + euclideanDistance);
                isNovel = false;
                break;
              }
            }
            if (isNovel) {
              globalResult.add(videoInfoList.get(i).getVideoId());
            }
          }
          logger.info(
              "Global visual similar video list size: " + globalResult.size()
                  + ", video list: " + (new Gson()).toJson(globalResult));
        }
        globalResult.addAll(textResult);
        logger.info("Similar video list size: " + globalResult.size()
            + ", video list: " + (new Gson()).toJson(globalResult)
            + ", cost " + (System.currentTimeMillis() - startTime) + " ms");

      }
    } catch (SQLException e) {
      logger.error("Hive SQL eroor." + e);
    }
  }

  public static void main(String[] args) {
    // detection
    List<Integer> invalidVideoId = new ArrayList<Integer>();
    invalidVideoId.add(779);

    int minId = 1;
    int maxId = 100;

    List<String> videoIdList = new ArrayList<String>();
    for (int i = minId; i <= maxId; i++) {
      if (!invalidVideoId.contains(i)) {
        videoIdList.add(Integer.toString(i));
      }
    }

    ExecutorService pool = MyThreadPool.getPool();
    TextGlobalDetection t = new TextGlobalDetection(videoIdList);
    pool.submit(t);
  }

}
