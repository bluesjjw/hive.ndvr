/**
 * @Title: KeyFrameEntity.java 
 * @Package cn.pku.net.db.hive.ndvr.entity 
 * @Description: TODO
 * @author Jiawei Jiang    
 * @date 2014年12月23日 下午12:50:59 
 * School of EECS, Peking University
 * Copyright (c) All Rights Reserved.
 */
package cn.pku.net.db.hive.ndvr.entity;

import java.util.Comparator;
import java.util.regex.Pattern;

/**
 * @ClassName: KeyFrameEntity 
 * @Description: TODO
 * @author Jiawei Jiang
 * @date 2014年12月23日 下午12:50:59
 */
public class KeyFrameEntity implements Comparator<KeyFrameEntity> {

    private String keyFrameName;
    private String videoId;
    private String videoFileName;
    private String serialId;     //serial number of the keyframe in video

    public static KeyFrameEntity parse(String[] infos) {
        KeyFrameEntity ent = null;
        if (infos.length == 4 && isInteger(infos[0])) {
            ent = new KeyFrameEntity();
            ent.setSerialId(infos[0]);
            ent.setKeyFrameName(infos[1]);
            ent.setVideoId(infos[2]);
            ent.setVideoFileName(infos[3]);
        }
        return ent;
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * Getter method for property <tt>keyFrameName</tt>.
     * 
     * @return property value of keyFrameName
     */
    public String getKeyFrameName() {
        return keyFrameName;
    }

    /**
     * Setter method for property <tt>keyFrameName</tt>.
     * 
     * @param keyFrameName value to be assigned to property keyFrameName
     */
    public void setKeyFrameName(String keyFrameName) {
        this.keyFrameName = keyFrameName;
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
     * Getter method for property <tt>videoFileName</tt>.
     * 
     * @return property value of videoFileName
     */
    public String getVideoFileName() {
        return videoFileName;
    }

    /**
     * Setter method for property <tt>videoFileName</tt>.
     * 
     * @param videoFileName value to be assigned to property videoFileName
     */
    public void setVideoFileName(String videoFileName) {
        this.videoFileName = videoFileName;
    }

    /**
     * Getter method for property <tt>serialId</tt>.
     * 
     * @return property value of serialId
     */
    public String getSerialId() {
        return serialId;
    }

    /**
     * Setter method for property <tt>serialId</tt>.
     * 
     * @param serialId value to be assigned to property serialId
     */
    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    /** 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(KeyFrameEntity o1, KeyFrameEntity o2) {
        int serialId1 = Integer.parseInt(o1.getSerialId());
        int serialId2 = Integer.parseInt(o2.getSerialId());
        if (serialId1 < serialId2) {
            return -1;
        } else if (serialId1 > serialId2) {
            return 1;
        }
        return 0;
    }
}
