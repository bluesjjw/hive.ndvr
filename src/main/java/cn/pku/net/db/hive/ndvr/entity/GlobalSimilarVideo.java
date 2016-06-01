/**
 * @Title: GlobalSimilarVideo.java 
 * @Package cn.pku.net.db.hive.ndvr.entity 
 * @Description: TODO
 * @author Jiawei Jiang    
 * @date 2014年12月30日 下午6:08:58 
 * School of EECS, Peking University
 * Copyright (c) All Rights Reserved.
 */
package cn.pku.net.db.hive.ndvr.entity;

import java.util.Comparator;

/**
 * @ClassName: GlobalSimilarVideo 
 * @Description: TODO
 * @author Jiawei Jiang
 * @date 2014年12月30日 下午6:08:58
 */
public class GlobalSimilarVideo implements Comparator<GlobalSimilarVideo> {

    private String videoId;
    private float  globalSigEucliDistance;

    public GlobalSimilarVideo() {
    }

    /**
     * @param videoInfoEnt
     * @param globalSigEucliDistance
     */
    public GlobalSimilarVideo(String videoId, float globalSigEucliDistance) {
        super();
        this.videoId = videoId;
        this.globalSigEucliDistance = globalSigEucliDistance;
    }

    /**
     * Getter method for property <tt>videoId</tt>.
     * 
     * @return property value of videoId
     */
    public String getVideoId() {
        return videoId;
    }

    /**
     * Setter method for property <tt>videoId</tt>.
     * 
     * @param videoId value to be assigned to property videoId
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    /**
     * Getter method for property <tt>globalSigEucliDistance</tt>.
     * 
     * @return property value of globalSigEucliDistance
     */
    public float getGlobalSigEucliDistance() {
        return globalSigEucliDistance;
    }

    /**
     * Setter method for property <tt>globalSigEucliDistance</tt>.
     * 
     * @param globalSigEucliDistance value to be assigned to property globalSigEucliDistance
     */
    public void setGlobalSigEucliDistance(float globalSigEucliDistance) {
        this.globalSigEucliDistance = globalSigEucliDistance;
    }

    /** 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(GlobalSimilarVideo arg0, GlobalSimilarVideo arg1) {
        float dist0 = arg0.getGlobalSigEucliDistance();
        float dist1 = arg1.getGlobalSigEucliDistance();
        if (dist0 < dist1) {
            return -1;
        } else if (dist0 > dist1) {
            return 1;
        }
        return 0;
    }
}
