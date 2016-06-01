/**
 * @Title: KeyFrameDownload.java 
 * @Package cn.pku.net.db.hive.ndvr.util 
 * @Description: Download keyframes of the CC_WEB_VIDEO dataset
 * @author Jiawei Jiang    
 * @date 2014年10月15日 上午10:53:48 
 * School of EECS, Peking University
 * Copyright (c) All Rights Reserved.
 */
package cn.pku.net.db.hive.ndvr.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * @ClassName: KeyFrameDownload
 * @Description: Download keyframes of the CC_WEB_VIDEO dataset
 * @author Jiawei Jiang
 * @date 2014年10月15日 上午10:53:48
 */
public class KeyFrameDownload {

	private static Logger logger = Logger.getLogger(KeyFrameDownload.class);

	private final static String keyframeListPath = "E:\\云盘\\文档\\pku\\paper\\NDVR\\实验\\Shot_Info.txt";
	private final static String keyframeSaveDir = "E:\\dataset\\cc_web_video\\keyframe\\";

	public void downloadKeyFrame() {
		File keyframeListFile = new File(keyframeListPath);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(keyframeListFile));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] infoArr = line.split("\t");
				if (Integer.parseInt(infoArr[2]) / 100 < 119)
					continue;
				String kfUrl = "http://vireo.cs.cityu.edu.hk/webvideo/Keyframes/"
						+ Integer.parseInt(infoArr[2]) / 100 + "/" + infoArr[1];
				String kfSaveDirStr = keyframeSaveDir
						+ Integer.parseInt(infoArr[2]) / 100 + "\\";
				File kfSaveDir = new File(kfSaveDirStr);
				if (!kfSaveDir.exists()) {
					kfSaveDir.mkdirs();
					logger.info("创建文件夹: " + kfSaveDirStr);
				}
				String kfSaveFile = kfSaveDirStr + infoArr[1];
				Runnable r = new DownloadThread(kfUrl, kfSaveFile);
				MyThreadPool.getInstance().getPool().submit(r);
			}
		} catch (FileNotFoundException e) {
			logger.error("Shot_Info file not found. ", e);
		} catch (IOException e) {
			logger.error("IOException when read Shot_Info file. ", e);
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
		KeyFrameDownload kfd = new KeyFrameDownload();
		kfd.downloadKeyFrame();
	}

}
