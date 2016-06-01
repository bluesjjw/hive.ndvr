/**
 * @Title: HSV4MongoEntity.java 
 * @Package cn.pku.net.db.hive.ndvr.entity 
 * @Description: TODO
 * @author Jiawei Jiang    
 * @date 2014年10月26日 下午7:03:11 
 * School of EECS, Peking University
 * Copyright (c) All Rights Reserved.
 */
package cn.pku.net.db.hive.ndvr.entity;

/**
 * @ClassName: HSV4MongoEntity
 * @Description: TODO
 * @author Jiawei Jiang
 * @date 2014年10月26日 下午7:03:11
 */
public class VideoHSVSigEntity {

    private String       videoId;
    private HSVSigEntity signature;

    public VideoHSVSigEntity(String[] lines) {
        this.videoId = lines[0];
        this.signature = new HSVSigEntity(lines);
    }

    public VideoHSVSigEntity() {

    }

    public VideoHSVSigEntity(String vid, HSVSigEntity sigEnt) {
        this.videoId = vid;
        this.signature = sigEnt;
    }

    /**
     * Getter method for property <tt>vid</tt>.
     * 
     * @return property value of vid
     */
    public String getVideoId() {
        return videoId;
    }

    /**
     * Setter method for property <tt>vid</tt>.
     * 
     * @param vid
     *            value to be assigned to property vid
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    /**
     * Getter method for property <tt>sig</tt>.
     * 
     * @return property value of sig
     */
    public HSVSigEntity getSig() {
        return signature;
    }

    /**
     * Setter method for property <tt>sig</tt>.
     * 
     * @param sig
     *            value to be assigned to property sig
     */
    public void setSig(HSVSigEntity sig) {
        this.signature = sig;
    }

}
