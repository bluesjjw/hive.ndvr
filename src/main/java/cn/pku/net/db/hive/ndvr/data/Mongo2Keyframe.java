package cn.pku.net.db.hive.ndvr.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import cn.pku.net.db.hive.ndvr.dao.KeyFrameDao;
import cn.pku.net.db.hive.ndvr.entity.KeyFrameEntity;

public class Mongo2Keyframe {

  public static void toTextFile(String fileName) throws IOException {
    File file = new File(fileName);
    if (!file.exists()) {
      file.createNewFile();
    }
    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    KeyFrameDao dao = new KeyFrameDao();
    List<KeyFrameEntity> keyframes = dao.getAllKeyFrame();
    for (KeyFrameEntity keyframe : keyframes) {
      StringBuilder sb = new StringBuilder();
      sb.append(keyframe.getVideoId() + "\t");
      sb.append(keyframe.getKeyFrameName() + "\t");
      sb.append(keyframe.getVideoFileName() + "\t");
      sb.append(keyframe.getSerialId());
      writer.write(sb.toString());
      writer.newLine();
    }
    writer.flush();
    writer.close();
  }

  public static void main(String[] args) throws IOException {
    String fileName = "keyframe.txt";
    toTextFile(fileName);
  }

}
