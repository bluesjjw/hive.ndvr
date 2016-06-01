package cn.pku.net.db.hive.ndvr.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import cn.pku.net.db.hive.ndvr.dao.VideoInfoDao;
import cn.pku.net.db.hive.ndvr.entity.VideoInfoEntity;

public class Mongo2VideoInfo {

  private static Logger logger = Logger.getLogger(Mongo2VideoInfo.class);

  public static void toTextFile(String fileName) throws IOException {
    File file = new File(fileName);
    if (!file.exists()) {
      file.createNewFile();
    }
    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    VideoInfoDao dao = new VideoInfoDao();
    List<VideoInfoEntity> videos = dao.getAllVideoInfo();
    for (VideoInfoEntity video : videos) {
      StringBuilder sb = new StringBuilder();
      sb.append(video.getVideoId() + "\t");
      sb.append(video.getTopicId() + "\t");
      sb.append(video.getSource() + "\t");
      sb.append(video.getVideoFileName() + "\t");
      sb.append(video.getDuration() + "\t");
      sb.append(video.getFormat() + "\t");
      sb.append(video.getTitle() + "\t");
      sb.append(video.getUrl());
      writer.write(sb.toString());
      writer.newLine();
    }
    writer.flush();
    writer.close();
  }

  public static void main(String[] args) throws IOException {
    String fileName = "videoinfo.txt";
    toTextFile(fileName);
  }
}
