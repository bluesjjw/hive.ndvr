/**
 * @Title: SIFTSigEntity.java 
 * @Package cn.pku.net.db.hive.ndvr.entity 
 * @Description: 一个帧图像的标签
 * @author Jiawei Jiang    
 * @date 2015年1月5日 上午10:12:27 
 * School of EECS, Peking University
 * Copyright (c) All Rights Reserved.
 */
package cn.pku.net.db.hive.ndvr.entity;

import java.util.ArrayList;
import java.util.List;

import cn.pku.net.db.hive.ndvr.image.analyze.sift.scale.KDFeaturePoint;

/**
 * @ClassName: SIFTSigEntity 
 * @Description: 一个帧图像的标签
 * @author Jiawei Jiang
 * @date 2015年1月5日 上午10:12:27
 */
public class SIFTSigEntity {

    private List<KDFeaturePoint> sig = new ArrayList<KDFeaturePoint>();

    /**
     * @param sig
     */
    public SIFTSigEntity(List<KDFeaturePoint> sig) {
        this.sig = sig;
    }

    /**
     * Getter method for property <tt>sig</tt>.
     * 
     * @return property value of sig
     */
    public List<KDFeaturePoint> getSig() {
        return sig;
    }

    /**
     * Setter method for property <tt>sig</tt>.
     * 
     * @param sig value to be assigned to property sig
     */
    public void setSig(List<KDFeaturePoint> sig) {
        this.sig = sig;
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
