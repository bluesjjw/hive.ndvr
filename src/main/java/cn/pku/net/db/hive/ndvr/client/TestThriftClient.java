package cn.pku.net.db.hive.ndvr.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class TestThriftClient {

  private static Logger logger = Logger.getLogger(TestThriftClient.class);
  private static String HIVE_SERVER_HOST = "162.105.146.209";
  private static int HIVE_SERVER_PORT = 10000;
  private static String driverName = "org.apache.hive.jdbc.HiveDriver";

  public static void main(String[] args) throws SQLException {
    try {
      Class.forName(driverName);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.exit(1);
    }
    Connection con = DriverManager.getConnection(
        "jdbc:hive2://" + HIVE_SERVER_HOST + ":" + HIVE_SERVER_PORT + "/src",
        "hive", "hive");
    Statement stmt = con.createStatement();
    ResultSet res = stmt.executeQuery("show tables");
    while (res.next()) {
      logger.info(res.getString(1));
    }
  }

}
