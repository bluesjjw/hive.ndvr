/**
 * @Title: VideoDownload.java 
 * @Package cn.pku.net.db.hive.ndvr.util 
 * @Description: Download video and keyframes of the CC_WEB_VIDEO dataset
 * @author Jiawei Jiang    
 * @date 2014年10月13日 下午2:49:35 
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
 * @ClassName: VideoDownload
 * @Description: Download video and keyframes of the CC_WEB_VIDEO dadaset
 * @author Jiawei Jiang
 * @date 2014年10月13日 下午2:49:35
 */
public class VideoDownload {

	private static Logger logger = Logger.getLogger(VideoDownload.class);

	private static final String videoListPath = "E:\\云盘\\文档\\pku\\paper\\NDVR\\实验\\Video_List.txt";
	private static final String videoSaveDir = "E:\\dataset\\cc_web_video\\video\\";

	public void downloadVideo() {
		File videoListFile = new File(videoListPath);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(videoListFile));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] infoArr = line.split("\t");
				// if (Integer.parseInt(infoArr[0]) <= 10578)
				// continue;
				String videoFileUrl = "http://vireo.cs.cityu.edu.hk/webvideo/videos/"
						+ infoArr[1] + "/" + infoArr[3];
				String videoSaveFile = videoSaveDir + infoArr[1] + "\\"
						+ infoArr[3];
				Runnable r = new DownloadThread(videoFileUrl,
						videoSaveFile);
				MyThreadPool.getInstance().getPool().submit(r);
			}
		} catch (FileNotFoundException e) {
			logger.error("VideoList file not found. ", e);
		} catch (IOException e) {
			logger.error("IOException when read VideoList file. ", e);
		}
	}

	public static void main(String[] args) {
		VideoDownload vdl = new VideoDownload();
		vdl.downloadVideo();
	}
}
