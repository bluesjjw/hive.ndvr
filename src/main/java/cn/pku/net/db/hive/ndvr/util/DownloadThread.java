/**
 * @Title: VideoDownloadThread.java 
 * @Package cn.pku.net.db.hive.ndvr.util 
 * @Description: 下载线程
 * @author Jiawei Jiang    
 * @date 2014年10月13日 下午7:53:32 
 * School of EECS, Peking University
 * Copyright (c) All Rights Reserved.
 */
package cn.pku.net.db.hive.ndvr.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

/**
 * @ClassName: VideoDownloadThread
 * @Description: TODO
 * @author Jiawei Jiang
 * @date 2014年10月13日 下午7:53:32
 */
public class DownloadThread implements Runnable {

	private static Logger logger = Logger.getLogger(DownloadThread.class);
	private final String fileUrl;
	private final String saveFile;

	/**
	 * @param videoUrl
	 * @param saveDir
	 */
	public DownloadThread(String fileUrl, String saveFile) {
		this.fileUrl = fileUrl;
		this.saveFile = saveFile;
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		URL url = null;
		try {
			url = new URL(fileUrl);

			URLConnection conn = url.openConnection();
			InputStream inStream = conn.getInputStream();
			FileOutputStream outStream = new FileOutputStream(saveFile);
			byte[] buffer = new byte[1024];
			int byteCount = 0;
			int byteSum = 0;
			while ((byteCount = inStream.read(buffer)) != -1) {
				byteSum += byteCount;
				outStream.write(buffer, 0, byteCount);
			}
			logger.info("Download file success. FileName: " + saveFile
					+ ". FileSize: " + byteSum + " Byte.");
		} catch (MalformedURLException e) {
			logger.error("MalformedURL. ", e);
		} catch (IOException e) {
			logger.error("IOException when download file. ", e);
		}
	}

}
