/**
 * @Title: Const.java 
 * @Package cn.pku.net.db.hive.ndvr.common 
 * @Description: TODO
 * @author Jiawei Jiang    
 * @date 2014年12月22日 下午3:02:15 
 * School of EECS, Peking University
 * Copyright (c) All Rights Reserved.
 */
package cn.pku.net.db.hive.ndvr.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: Const
 * @Description: TODO
 * @author Jiawei Jiang
 * @date 2014年12月22日 下午3:02:15
 */
public class Const {

    public static class MONGO {
        public static String MONGO_TASK_HOST              = "162.105.146.209";
        public static String MONGO_HOST                   = "localhost";
        public static int    MONGO_PORT                   = 27017;
        public static String MONGO_DATABASE               = "NDVR";
    public static String MONGO_TASK_COLLECTION = "task"; // 存放NDVR任务，task对应的video存在query表中
    public static String MONGO_VIDEO_COLLECTION = "videoInfo"; // 存放视频信息
    public static String MONGO_KEYFRAME_COLLECTION = "keyFrame"; // 存放关键帧
    public static String MONGO_HSVSIG_COLLECTION = "hsvSignature"; // 存放hsv标签
    public static String MONGO_TASK_RESULT_COLLECTION = "taskResult"; // 存放task结果
    }

    public static class CC_WEB_VIDEO {
        //PC
        //        public static String VIDEO_INFO_PATH            = "E:\\dataset\\cc_web_video\\Video_Complete.txt";
        //        public static String KEYFRAME_INFO_PATH         = "E:\\dataset\\cc_web_video\\Shot_Info.txt";
        //        public static String HSV_SIGNATURE_PATH_PREFIX  = "E:\\dataset\\cc_web_video\\HSV.txt";
        //        public static String KEYFRAME_FILE_PATH_PREFIX  = "E:\\dataset\\cc_web_video\\keyframe\\";
        //        public static String SIFT_SIGNATURE_PATH_PREFIX = "E:\\dataset\\cc_web_video\\siftsignature\\";

        //Server
        public static String VIDEO_INFO_PATH            = "/home/jiangjw/dataset/cc_web_video/Video_Complete.txt";
        public static String KEYFRAME_INFO_PATH         = "/home/jiangjw/dataset/cc_web_video/Shot_Info.txt";
        public static String HSV_SIGNATURE_PATH_PREFIX  = "/home/jiangjw/dataset/cc_web_video/HSV.txt";
        public static String KEYFRAME_FILE_PATH_PREFIX  = "/home/jiangjw/dataset/cc_web_video/keyframe/";
        public static String SIFT_SIGNATURE_PATH_PREFIX = "/home/jiangjw/dataset/cc_web_video/siftsignature/";

    }

    public static class STORM_CONFIG {
    public static boolean IS_LOCAL_MODE = false; // 是否是本地模式
    public static boolean IS_DEBUG = true; // 是否启动调试模式
    public static boolean IS_MESSAGE_REDUCTION = false; // 是否采用控制信息压缩算法
    public static int GET_TASK_INTERVAL = 100; // 查询任务的间隔，单位为毫秒
        public static String  RETRIEVAL_TASK_FLAG                    = "retrieval";
        public static String  DETECTION_TASK_FLAG                    = "detection";
    public static int TEXT_COMPARED_WINDOW = 3; // 比较两个视频文本信息时,单词的窗口大小
    public static int VIDEO_DURATION_WINDOW = 10; // 确定待比较的视频时,视频时长的比较窗口大小
    public static int BOLT_DURATION_WINDOW = 60; // 每个bolt负责一段时间的视频,单位为秒
    public static int FRAME_COMPARED_WINDOW = 3; // 比较local标签时,帧间比较窗口的大小
    public static float GLOBALSIG_EUCLIDEAN_THRESHOLD = (float) 0.5; // 全局标签的欧氏距离阈值
    public static float GLOBALSIG_EUCLIDEAN_TRUST_THRESHOLD = (float) 0.2; // 全局标签的欧氏距离的可信阈值,小于此阈值就可以认为是近似重复视频
    public static int LOCALSIG_KEYFRAME_MAXNUM = 10; // 计算SIFT标签时,最多使用前几个帧,避免有的视频帧太多,导致计算时间太长
    public static float TEXT_SIMILARITY_THRESHOLD = (float) 0.5; // 文本信息相似度的阈值
    public static float LOCALSIG_KEYFRAME_SIMILARITY_THRESHOLD = (float) 0.1; // 两个帧图像之间,相似的SIFT
                                                                              // keyponts数量达到一定比例即认为两个帧图像相似
    public static float LOCALSIG_VIDEO_SIMILARITY_THRESHOLd = (float) 0.5; // 两个视频之间,相似的帧图像数量达到一定的比例即认为两个视频相似
    public static boolean IS_FILTER_AND_REFINE = true; // 在使用多种特征时,是否是filter-and-refine的策略
    }

    public static class CTRLMSG_CONFIG {
    public static boolean IS_REDUCTIION = true; // 是否 缩减控制信息
        public static int[][]             TOPOLOGY_GRAPH        = { { 0, 1, 0, 0 }, { 0, 0, 1, 0 },
                                                                        { 0, 0, 0, 1 },
                                                                        { 0, 0, 0, 0 } };
        public static String[]            TOPOLOGY_COMPONENT    = { "GetTaskSpout",
                                                                        "GlobalFeatureBolt",
                                                                        "GlobalSigDistance",
                                                                        "Algorithm4ResultBolt" };
        public static Map<String, String> COMPONENT_KEY_NEEDED  = new HashMap<String, String>();
        public static Map<String, String> COMPONENT_NEW_KEY     = new HashMap<String, String>();
        public static Map<String, String> COMPONENT_DISCARD_KEY = new HashMap<String, String>();

        {

            COMPONENT_KEY_NEEDED.put("GlobalFeatureRetrievalBolt", "queryVideo");
            COMPONENT_NEW_KEY.put("GlobalFeatureRetrievalBolt", "keyframeList||globalSignature");

            COMPONENT_KEY_NEEDED.put("GlobalSigDistanceRetrievalBolt",
                "queryVideo||globalSignature");
            COMPONENT_NEW_KEY.put("GlobalSigDistanceRetrievalBolt", "globalSimilarVideoList");

            COMPONENT_KEY_NEEDED.put("LocalFeatureRetrievalBolt", "queryVideo||keyframeList");
            COMPONENT_NEW_KEY.put("LocalFeatureRetrievalBolt", "localSignature");

            COMPONENT_KEY_NEEDED.put("LocalSigDistanceRetrievalBolt", "queryVideo||localSignature");
            COMPONENT_NEW_KEY.put("LocalSigDistanceRetrievalBolt", "localSimilarVideoList");

            COMPONENT_KEY_NEEDED.put("TextSimilarityRetrievalBolt", "queryVideo");
            COMPONENT_NEW_KEY.put("TextSimilarityRetrievalBolt", "textSimilarVideoList");

            //global
            COMPONENT_DISCARD_KEY.put("GlobalSigDistanceRetrievalBolt",
                "queryVideo,keyframeList,globalSignature");

            //global+local
            //            COMPONENT_DISCARD_KEY.put("LocalSigDistanceRetrievalBolt",
            //                "queryVideo,globalSimilarVideoList,localSignature");
            //            COMPONENT_DISCARD_KEY.put("LocalFeatureRetrievalBolt", "keyframeList");
            //            COMPONENT_DISCARD_KEY.put("GlobalSigDistanceRetrievalBolt", "globalSignature");

            //text+global
            //            COMPONENT_DISCARD_KEY.put("GlobalSigDistanceRetrievalBolt",
            //                "queryVideo,keyframeList,globalSignature,textSimilarVideoList");

            //text
            //            COMPONENT_DISCARD_KEY.put("TextSimilarityRetrievalBolt", "queryVideo");
        }

        public static Map<String, String> discardInvalidKey(String boltId,
                                                            Map<String, String> ctrlMsg) {
            if (!COMPONENT_DISCARD_KEY.containsKey(boltId)) {
                return ctrlMsg;
            }
            String[] discaredKeys = COMPONENT_DISCARD_KEY.get(boltId).split(",");
            for (String key : discaredKeys) {
                ctrlMsg.remove(key);
            }
            return ctrlMsg;
        }
    }
}
