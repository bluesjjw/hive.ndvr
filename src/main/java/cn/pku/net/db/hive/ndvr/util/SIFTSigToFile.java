/**
 * @Title: SIFTSig2Mongo.java 
 * @Package cn.pku.net.db.hive.ndvr.util 
 * @Description: TODO
 * @author Jiawei Jiang    
 * @date 2015年1月5日 上午10:28:11 
 * School of EECS, Peking University
 * Copyright (c) All Rights Reserved.
 */
package cn.pku.net.db.hive.ndvr.util;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import cn.pku.net.db.hive.ndvr.common.Const;
import cn.pku.net.db.hive.ndvr.dao.KeyFrameDao;
import cn.pku.net.db.hive.ndvr.dao.VideoInfoDao;
import cn.pku.net.db.hive.ndvr.entity.KeyFrameEntity;
import cn.pku.net.db.hive.ndvr.entity.SIFTSigEntity;
import cn.pku.net.db.hive.ndvr.entity.VideoInfoEntity;
import cn.pku.net.db.hive.ndvr.entity.VideoSIFTSigEntity;
import cn.pku.net.db.hive.ndvr.image.analyze.sift.*;
import cn.pku.net.db.hive.ndvr.image.analyze.sift.render.*;
import cn.pku.net.db.hive.ndvr.image.analyze.sift.scale.KDFeaturePoint;

import com.google.gson.Gson;

/**
 * @ClassName: SIFTSig2File
 * @Description: 计算视频的SIFT标签并存入文件
 * @author Jiawei Jiang
 * @date 2015年1月5日 上午10:28:11
 */
public class SIFTSigToFile implements Runnable {

    private static final Logger  logger = Logger.getLogger(SIFTSigToFile.class);

    private VideoInfoEntity      videoInfo;
    private List<KeyFrameEntity> keyframeList;

    /**
     * @param videoInfo
     * @param keyframeList
     */
    public SIFTSigToFile(VideoInfoEntity videoInfo, List<KeyFrameEntity> keyframeList) {
        super();
        this.videoInfo = videoInfo;
        this.keyframeList = keyframeList;
    }

    /** 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
    // 保存每个帧图像的SIFT标签
        List<SIFTSigEntity> keyframeSigList = new ArrayList<SIFTSigEntity>();
        int maxKeyFrameNum = this.keyframeList.size();
        if (maxKeyFrameNum > Const.STORM_CONFIG.LOCALSIG_KEYFRAME_MAXNUM) {
            maxKeyFrameNum = Const.STORM_CONFIG.LOCALSIG_KEYFRAME_MAXNUM;
        }
        for (int i = 0; i < maxKeyFrameNum; i++) {
      // 计算每个帧的sift标签
            KeyFrameEntity keyframeEnt = this.keyframeList.get(i);
            String keyframeFile = Const.CC_WEB_VIDEO.KEYFRAME_FILE_PATH_PREFIX
                                  + Integer.parseInt(keyframeEnt.getVideoId()) / 100 + "\\"
                                  + keyframeEnt.getKeyFrameName();
            try {
                File file = new File(keyframeFile);
                if (!file.exists()) {
                    continue;
                }
                BufferedImage img = ImageIO.read(new File(keyframeFile));
                RenderImage ri = new RenderImage(img);
                SIFT sift = new SIFT();
                sift.detectFeatures(ri.toPixelFloatArray(null));
                List<KDFeaturePoint> al = sift.getGlobalKDFeaturePoints();
                if (null != al && !al.isEmpty()) {
          SIFTSigEntity siftSig = new SIFTSigEntity(al); // 一个帧图像的标签
                    //                    String gsonStr = (new Gson()).toJson(siftSig);
                    //                    System.out.println(gsonStr);
                    keyframeSigList.add(siftSig);
                }
            } catch (IOException e) {
                logger.error("IO error when read image: " + keyframeFile);
            } catch (ArrayIndexOutOfBoundsException e) {
                logger.info("Index out of bounds when generate SIFT signature: "
                            + videoInfo.getVideoId());
                return;
            } catch (IllegalStateException e) {
                logger.info("IllegalStateException when generate SIFT signature: "
                            + videoInfo.getVideoId());
                return;
            }
        }
        if (!keyframeSigList.isEmpty()) {
      // 生成整个视频的SIFT标签
            VideoSIFTSigEntity videoSIFTSig = new VideoSIFTSigEntity(this.videoInfo.getVideoId(),
                keyframeSigList);
      // 将标签转化为字符串
            Gson gson = new Gson();
            String gsonStr = gson.toJson(videoSIFTSig);

      // 构造输出文件路径
            String outputFilePath = Const.CC_WEB_VIDEO.SIFT_SIGNATURE_PATH_PREFIX
                                    + Integer.parseInt(this.videoInfo.getVideoId()) / 100 + "\\"
                                    + this.videoInfo.getVideoId() + ".txt";
            File outputFile = new File(outputFilePath);
            File outputFileParent = outputFile.getParentFile();
      // 如果没有文件夹,则新建文件夹
            if (null != outputFileParent && !outputFileParent.exists()) {
                outputFileParent.mkdirs();
            }
            if (!outputFile.exists()) {
                try {
                    outputFile.createNewFile();
                } catch (IOException e) {
                    logger.error("can not create output file: " + outputFilePath, e);
                }
            }

      // 将标签写入文件
            try {
                FileWriter fileWriter = new FileWriter(outputFile, true);
                BufferedWriter bufferwriter = new BufferedWriter(fileWriter);
                bufferwriter.write(gsonStr);
                bufferwriter.newLine();
                bufferwriter.close();
                fileWriter.close();
            } catch (IOException e) {
                logger.error(
                    "IO error when write sift signature for video: " + this.videoInfo.getVideoId(),
                    e);
            }
            logger.info("Generate SIFT signature for video: " + this.videoInfo.getVideoId());

        } else {
            logger
                .info("Can not generate SIFT signature for video: " + this.videoInfo.getVideoId());
        }
    }

    /**
     * @Title: main 
     * @Description: TODO
     * @param @param args     
     * @return void   
     * @throws 
     * @param args
     */
    public static void main(String[] args) {
        VideoInfoEntity videoInfo = (new VideoInfoDao()).getVideoInfoById("1111");
        List<KeyFrameEntity> keyframeList = (new KeyFrameDao()).getKeyFrameByVideoId(videoInfo
            .getVideoId());
        Collections.sort(keyframeList, new KeyFrameEntity());
        for (KeyFrameEntity ent : keyframeList) {
            System.out.println(ent.getSerialId());
        }
        Runnable r = new SIFTSigToFile(videoInfo, keyframeList);
        r.run();
    }

}
