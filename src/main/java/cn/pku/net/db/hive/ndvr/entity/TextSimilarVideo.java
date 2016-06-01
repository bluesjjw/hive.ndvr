/**
 * @Title: TextSimilarVideo.java 
 * @Package cn.pku.net.db.hive.ndvr.entity 
 * @Description: TODO
 * @author Jiawei Jiang    
 * @date 2015年1月8日 下午12:34:29 
 * School of EECS, Peking University
 * Copyright (c) All Rights Reserved.
 */
package cn.pku.net.db.hive.ndvr.entity;

import java.util.Comparator;

/**
 * @ClassName: TextSimilarVideo 
 * @Description: TODO
 * @author Jiawei Jiang
 * @date 2015年1月8日 下午12:34:29
 */
public class TextSimilarVideo implements Comparator<TextSimilarVideo> {

    private String videoId;
    private float  textSimilarity;

    public TextSimilarVideo() {

    }

    /**
     * @param videoId
     * @param textSimilarity
     */
    public TextSimilarVideo(String videoId, float textSimilarity) {
        super();
        this.videoId = videoId;
        this.textSimilarity = textSimilarity;
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
     * Getter method for property <tt>textSimilarity</tt>.
     * 
     * @return property value of textSimilarity
     */
    public float getTextSimilarity() {
        return textSimilarity;
    }

    /**
     * Setter method for property <tt>textSimilarity</tt>.
     * 
     * @param textSimilarity value to be assigned to property textSimilarity
     */
    public void setTextSimilarity(float textSimilarity) {
        this.textSimilarity = textSimilarity;
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
    public int compare(TextSimilarVideo arg0, TextSimilarVideo arg1) {
        if (arg0.getTextSimilarity() > arg1.getTextSimilarity()) {
            return -1;
        } else if (arg0.getTextSimilarity() < arg1.getTextSimilarity()) {
            return 1;
        }
        return 0;
    }

}
