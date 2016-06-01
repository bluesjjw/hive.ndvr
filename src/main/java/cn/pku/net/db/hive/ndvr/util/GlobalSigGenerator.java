/**
 * @Title: GlobalSigGenerator.java 
 * @Package cn.pku.net.db.hive.ndvr.util 
 * @Description: 生成全局标签
 * @author Jiawei Jiang    
 * @date 2014年12月28日 下午12:23:50 
 * School of EECS, Peking University
 * Copyright (c) All Rights Reserved.
 */
package cn.pku.net.db.hive.ndvr.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import cn.pku.net.db.hive.ndvr.common.Const;
import cn.pku.net.db.hive.ndvr.dao.KeyFrameDao;
import cn.pku.net.db.hive.ndvr.entity.HSVSigEntity;
import cn.pku.net.db.hive.ndvr.entity.KeyFrameEntity;

/**
 * @ClassName: GlobalSigGenerator 
 * @Description: 生成全局标签
 * @author Jiawei Jiang
 * @date 2014年12月28日 下午12:23:50
 */
/**
 * @ClassName: GlobalSigGenerator
 * @Description: TODO
 * @author Jiawei Jiang
 * @date 2014年12月28日 下午3:38:37
 */
public class GlobalSigGenerator {

    private static final Logger logger = Logger.getLogger(GlobalSigGenerator.class);

    public static HSVSigEntity generate(List<KeyFrameEntity> keyframeList) {
    float[] hsvSignature = new float[24]; // 视频的HSV标签,前18为H分类,3为S分类,3为V分类
    int count = 0; // 记录有效关键帧数量
        for (KeyFrameEntity keyframeEnt : keyframeList) {
            String keyframeFile = Const.CC_WEB_VIDEO.KEYFRAME_FILE_PATH_PREFIX
                                  + Integer.parseInt(keyframeEnt.getVideoId()) / 100 + "/"
                                  + keyframeEnt.getKeyFrameName();
            InputStream input = null;
            try {
                input = new FileInputStream(new File(keyframeFile));
                BufferedImage image = ImageIO.read(input);
        float[] imageHsv = new float[24]; // 一个关键帧的HSV标签
                for (int posx = 0; posx < image.getWidth(); posx++) {
                    for (int posy = 0; posy < image.getHeight(); posy++) {
                        Color rgbColor = new Color(image.getRGB(posx, posy));
                        float[] pixelHsv = new float[3];
                        Color.RGBtoHSB(rgbColor.getRed(), rgbColor.getGreen(), rgbColor.getBlue(),
                            pixelHsv);
                        hsvQuantization(pixelHsv, imageHsv);
                        //                        System.out.println(pixelHsv[0] + "|" + pixelHsv[1] + "|" + pixelHsv[2]);
                    }
                }
                for (int i = 0; i < imageHsv.length; i++) {
                    hsvSignature[i] += imageHsv[i] / (image.getWidth() * image.getHeight());
                }
                count++;
            } catch (FileNotFoundException e) {
                logger.error("File not found: " + keyframeFile, e);
            } catch (IOException e) {
                logger.error("IO error when read image: " + keyframeFile, e);
            }

        }
    // 没有有效的关键帧,返回null
        if (count == 0) {
            return null;
        }
        for (int i = 0; i < hsvSignature.length; i++) {
            hsvSignature[i] /= count;
            //            System.out.println(hsvSignature[i]);
        }
        HSVSigEntity signature = new HSVSigEntity(hsvSignature);
        return signature;
    }

    /**
   * @Title: hsvQuantization
   * @Description: 对HSV进行量化
   * @param @param
   *          pixelHsv
   * @param @param
   *          hsvSignature
   * @return void
   * @throws @param
   *           pixelHsv
   * @param hsvSignature
   */
    private static void hsvQuantization(float[] pixelHsv, float[] imageHsv) {
        float h = pixelHsv[0] * 360;
        float s = pixelHsv[1] * 3;
        float v = pixelHsv[2] * 3;
    // 量化H分量,以20等间隔量化
        imageHsv[(int) h / 20]++;
    // 量化V分量,以1等间隔量化
        imageHsv[(int) s + 18]++;
    // 量化S分量,以1等间隔量化
        imageHsv[v >= 3 ? 23 : (int) v + 21]++;
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
        KeyFrameDao dao = new KeyFrameDao();
        List<KeyFrameEntity> keyframeList = dao.getKeyFrameByVideoId("773");
        HSVSigEntity signature = GlobalSigGenerator.generate(keyframeList);
        System.out.println(signature.getBin1());
    }

}
