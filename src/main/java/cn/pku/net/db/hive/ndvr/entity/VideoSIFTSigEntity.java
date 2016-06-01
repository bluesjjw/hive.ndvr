/**
 * @Title: VideoSIFTSigEntity.java 
 * @Package cn.pku.net.db.hive.ndvr.entity 
 * @Description: TODO
 * @author Jiawei Jiang    
 * @date 2015年1月5日 上午10:14:25 
 * School of EECS, Peking University
 * Copyright (c) All Rights Reserved.
 */
package cn.pku.net.db.hive.ndvr.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: VideoSIFTSigEntity 
 * @Description: TODO
 * @author Jiawei Jiang
 * @date 2015年1月5日 上午10:14:25
 */
public class VideoSIFTSigEntity {

    private String              videoId;
    private List<SIFTSigEntity> signatures = new ArrayList<SIFTSigEntity>(); //每个帧一个标签

    /**
     * @param videoId
     * @param signature
     */
    public VideoSIFTSigEntity(String videoId, List<SIFTSigEntity> signature) {
        super();
        this.videoId = videoId;
        this.signatures = signature;
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
     * Getter method for property <tt>signature</tt>.
     * 
     * @return property value of signature
     */
    public List<SIFTSigEntity> getSignature() {
        return signatures;
    }

    /**
     * Setter method for property <tt>signature</tt>.
     * 
     * @param signature value to be assigned to property signature
     */
    public void setSignature(List<SIFTSigEntity> signature) {
        this.signatures = signature;
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

}
