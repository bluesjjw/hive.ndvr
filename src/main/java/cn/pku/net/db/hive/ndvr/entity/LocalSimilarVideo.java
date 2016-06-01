/**
 * @Title: LocalSimilarVideo.java 
 * @Package cn.pku.net.db.hive.ndvr.entity 
 * @Description: TODO
 * @author Jiawei Jiang    
 * @date 2015年1月5日 下午9:09:42 
 * School of EECS, Peking University
 * Copyright (c) All Rights Reserved.
 */
package cn.pku.net.db.hive.ndvr.entity;

import java.util.Comparator;

/**
 * @ClassName: LocalSimilarVideo 
 * @Description: TODO
 * @author Jiawei Jiang
 * @date 2015年1月5日 下午9:09:42
 */
public class LocalSimilarVideo implements Comparator<LocalSimilarVideo> {

    private String videoId;
    private float  localSigSimilarity; //相似的帧占总帧数的比例

    public LocalSimilarVideo() {

    }

    /**
     * @param videoId
     * @param localSigSimilarity
     */
    public LocalSimilarVideo(String videoId, float localSigSimilarity) {
        super();
        this.videoId = videoId;
        this.localSigSimilarity = localSigSimilarity;
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
     * Getter method for property <tt>localSigSimilarity</tt>.
     * 
     * @return property value of localSigSimilarity
     */
    public float getLocalSigSimilarity() {
        return localSigSimilarity;
    }

    /**
     * Setter method for property <tt>localSigSimilarity</tt>.
     * 
     * @param localSigSimilarity value to be assigned to property localSigSimilarity
     */
    public void setLocalSigSimilarity(float localSigSimilarity) {
        this.localSigSimilarity = localSigSimilarity;
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

    }

    /** 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(LocalSimilarVideo arg0, LocalSimilarVideo arg1) {
        //相似度较大的在前
        if (arg0.getLocalSigSimilarity() > arg1.getLocalSigSimilarity()) {
            return -1;
        } else if (arg0.getLocalSigSimilarity() < arg1.getLocalSigSimilarity()) {
            return 1;
        }
        return 0;
    }

}
