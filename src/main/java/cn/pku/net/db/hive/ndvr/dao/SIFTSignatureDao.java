/**
 * @Title: SIFTSignatureDao.java 
 * @Package cn.pku.net.db.hive.ndvr.dao 
 * @Description: TODO
 * @author Jiawei Jiang    
 * @date 2015年1月5日 上午10:18:33 
 * School of EECS, Peking University
 * Copyright (c) All Rights Reserved.
 */
package cn.pku.net.db.hive.ndvr.dao;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import cn.pku.net.db.hive.ndvr.common.Const;
import cn.pku.net.db.hive.ndvr.util.MyThreadPool;
import cn.pku.net.db.hive.ndvr.util.SIFTSigToFile;
import cn.pku.net.db.hive.ndvr.entity.KeyFrameEntity;
import cn.pku.net.db.hive.ndvr.entity.VideoInfoEntity;

import com.mongodb.MongoClient;

/**
 * @ClassName: SIFTSignatureDao
 * @Description: TODO
 * @author Jiawei Jiang
 * @date 2015年1月5日 上午10:18:33
 */
public class SIFTSignatureDao {

    private static final Logger logger      = Logger.getLogger(SIFTSignatureDao.class);

    private static MongoClient  mongoClient = null;

    public SIFTSignatureDao() {
        if (null == mongoClient) {
            try {
                mongoClient = new MongoClient(Const.MONGO.MONGO_HOST, Const.MONGO.MONGO_PORT);
            } catch (UnknownHostException e) {
                logger.error("MongoDB UnknownHost", e);
            }
        }
    }

  // 计算视频的局部SIFT标签,存入文件,一个文件存储一个视频的标签
    public void saveToFile() {
    // String doneFilePath =
    // "E:\\云盘\\文档\\pku\\code\\eclipse\\storm.ndvr\\log\\NDVR.log";
        //        File doneFile = new File(doneFilePath);
        //        List<String> doneFileList = new ArrayList<String>();
        //        try {
        //            BufferedReader reader = new BufferedReader(new FileReader(doneFile));
        //            String line = null;
        //            while ((line = reader.readLine()) != null) {
        //                int index = line.lastIndexOf(":");
        //                if (line.startsWith("[ERROR]") || index == -1) {
        //                    continue;
        //                }
        //                String videoId = line.substring(index + 2);
        //                doneFileList.add(videoId);
        //            }
        //            reader.close();
        //        } catch (FileNotFoundException e) {
        //            logger.error("", e);
        //        } catch (IOException e) {
        //            logger.error("", e);
        //        }
        List<VideoInfoEntity> videoList = (new VideoInfoDao()).getAllVideoInfo();
        for (VideoInfoEntity videoInfo : videoList) {
            //            if (doneFileList.contains(videoInfo.getVideoId())) {
            //                //                System.out.println("Video has been processed: " + videoInfo.getVideoId());
            //                continue;
            //            }
            List<KeyFrameEntity> keyframeList = (new KeyFrameDao()).getKeyFrameByVideoId(videoInfo
                .getVideoId());
            Collections.sort(keyframeList, new KeyFrameEntity());
            Runnable r = new SIFTSigToFile(videoInfo, keyframeList);
            MyThreadPool.getPool().submit(r);
            //            r.run();
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
        SIFTSignatureDao dao = new SIFTSignatureDao();
        dao.saveToFile();
        //11742/11745/11751
    }

}
