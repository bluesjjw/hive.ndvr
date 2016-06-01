package cn.pku.net.db.hive.ndvr.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import cn.pku.net.db.hive.ndvr.dao.HSVSignatureDao;
import cn.pku.net.db.hive.ndvr.entity.HSVSigEntity;
import cn.pku.net.db.hive.ndvr.entity.VideoHSVSigEntity;

public class Mongo2HSVSig {

  private static Logger logger = Logger.getLogger(Mongo2HSVSig.class);

  public static void toTextFile(String fileName) throws IOException {
    File file = new File(fileName);
    if (!file.exists()) {
      file.createNewFile();
    }
    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    HSVSignatureDao dao = new HSVSignatureDao();
    List<VideoHSVSigEntity> videoSigs = dao.getAllVideoHSVSig();
    for (VideoHSVSigEntity videoSig : videoSigs) {
      StringBuilder sb = new StringBuilder();
      sb.append(videoSig.getVideoId() + "\t");
      HSVSigEntity ent = videoSig.getSig();
      Gson gson = new Gson();
      String hsvStr = gson.toJson(ent);
      sb.append(hsvStr);
      writer.write(sb.toString());
      writer.newLine();
    }
    writer.flush();
    writer.close();
  }

  public static void main(String[] args) throws IOException {
    String fileName = "hsvsignature.txt";
    toTextFile(fileName);
  }

}
