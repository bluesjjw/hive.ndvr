/**
 * @Title: MongoDao.java 
 * @Package cn.pku.net.db.hive.ndvr.mongo 
 * @Description: mongodb operations for task collection
 * @author Jiawei Jiang    
 * @date 2014年12月22日 下午2:58:02 
 * School of EECS, Peking University
 * Copyright (c) All Rights Reserved.
 */
package cn.pku.net.db.hive.ndvr.dao;

import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.pku.net.db.hive.ndvr.common.Const;
import cn.pku.net.db.hive.ndvr.entity.TaskEntity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

/**
 * @ClassName: MongoDao
 * @Description: mongodb operations
 * @author Jiawei Jiang
 * @date 2014年12月22日 下午2:58:02
 */
public class TaskDao {

    private static final Logger logger      = Logger.getLogger(TaskDao.class);

    private static MongoClient  mongoClient = null;

    public TaskDao() {
        if (null == mongoClient) {
            try {
                mongoClient = new MongoClient(Const.MONGO.MONGO_TASK_HOST, Const.MONGO.MONGO_PORT);
            } catch (UnknownHostException e) {
                logger.error("MongoDB UnknownHost", e);
            }
        }
    }

    public TaskEntity getTaskById(String taskId) {
        if (null == mongoClient) {
            return null;
        }
        TaskEntity task = null;
        DB db = mongoClient.getDB(Const.MONGO.MONGO_DATABASE);
        DBCollection col = db.getCollection(Const.MONGO.MONGO_TASK_COLLECTION);
        BasicDBObject query = new BasicDBObject("taskId", taskId);
        DBObject queryResult = col.findOne(query);
        if (null != queryResult) {
            task = new TaskEntity();
            task.setTaskId(taskId);
            task.setTaskType((String) queryResult.get("taskType"));
            String videoIdListStr = queryResult.get("videoIdList").toString();
            Type videoIdListType = new TypeToken<List<String>>() {
            }.getType();
            List<String> videoIdList = (new Gson()).fromJson(videoIdListStr, videoIdListType);
            task.setVideoIdList(videoIdList);
            task.setStatus(queryResult.get("status").toString());
        }
        return task;
    }

    /**
   * @Title: getNewTask
   * @Description: TODO
   * @param @return
   * @return MongoTaskEntity
   * @throws 如果没有任务，返回空数组
   */
    public List<TaskEntity> getNewTask() {
        if (null == mongoClient) {
            return null;
        }
        List<TaskEntity> result = new ArrayList<TaskEntity>();
        DB db = mongoClient.getDB(Const.MONGO.MONGO_DATABASE);
        DBCollection col = db.getCollection(Const.MONGO.MONGO_TASK_COLLECTION);
        BasicDBObject query = new BasicDBObject("status", "0");
        DBCursor cursor = col.find(query);
        while (cursor.hasNext()) {
            DBObject obj = cursor.next();
            TaskEntity ent = new TaskEntity();
            ent.setTaskId(obj.get("taskId").toString());
            ent.setTaskType((String) obj.get("taskType"));
            String videoIdListStr = obj.get("videoIdList").toString();
            Type videoIdListType = new TypeToken<List<String>>() {
            }.getType();
            List<String> videoIdList = (new Gson()).fromJson(videoIdListStr, videoIdListType);
            ent.setVideoIdList(videoIdList);
            ent.setStatus(obj.get("status").toString());
            result.add(ent);
            //            logger.info(ent.getTaskId() + "|" + ent.getTaskType() + "|" + ent.getStatus());
      // 将任务status置为1
            BasicDBObject newDocument = new BasicDBObject();
            newDocument.append("$set", new BasicDBObject().append("status", "1"));
            BasicDBObject updateQuery = new BasicDBObject().append("taskId", ent.getTaskId());
            col.update(updateQuery, newDocument);
        }
        cursor.close();
        return result;
    }

    /**
   * @Title: getNewRetrievalTask
   * @Description: TODO
   * @param @return
   * @return MongoTaskEntity
   * @throws 如果没有retrieval任务，返回空数组
   */
    public List<TaskEntity> getNewRetrievalTask() {
        if (null == mongoClient) {
            return null;
        }
        List<TaskEntity> result = new ArrayList<TaskEntity>();
        DB db = mongoClient.getDB(Const.MONGO.MONGO_DATABASE);
        DBCollection col = db.getCollection(Const.MONGO.MONGO_TASK_COLLECTION);
        BasicDBObject query = new BasicDBObject("status", "0");
        DBCursor cursor = col.find(query);
        while (cursor.hasNext()) {
            DBObject obj = cursor.next();
            String taskType = (String) obj.get("taskType");
            if (!Const.STORM_CONFIG.RETRIEVAL_TASK_FLAG.equals(taskType)) {
                continue;
            }
            TaskEntity ent = new TaskEntity();
            ent.setTaskId(obj.get("taskId").toString());
            ent.setTaskType(taskType);
            String videoIdListStr = obj.get("videoIdList").toString();
            Type videoIdListType = new TypeToken<List<String>>() {
            }.getType();
            List<String> videoIdList = (new Gson()).fromJson(videoIdListStr, videoIdListType);
            ent.setVideoIdList(videoIdList);
            ent.setStatus(obj.get("status").toString());
            result.add(ent);
            //            logger.info(ent.getTaskId() + "|" + ent.getTaskType() + "|" + ent.getStatus());
      // 将任务status置为1
            BasicDBObject newDocument = new BasicDBObject();
            newDocument.append("$set", new BasicDBObject().append("status", "1"));
            BasicDBObject updateQuery = new BasicDBObject().append("taskId", ent.getTaskId());
            col.update(updateQuery, newDocument);
        }
        cursor.close();
        return result;
    }

    /**
   * @Title: getNewDetectionTask
   * @Description: TODO
   * @param @return
   * @return MongoTaskEntity
   * @throws 如果没有detection任务，返回空数组
   */
    public List<TaskEntity> getNewDetectionTask() {
        if (null == mongoClient) {
            return null;
        }
        List<TaskEntity> result = new ArrayList<TaskEntity>();
        DB db = mongoClient.getDB(Const.MONGO.MONGO_DATABASE);
        DBCollection col = db.getCollection(Const.MONGO.MONGO_TASK_COLLECTION);
        BasicDBObject query = new BasicDBObject("status", "0");
        DBCursor cursor = col.find(query);
        while (cursor.hasNext()) {
            DBObject obj = cursor.next();
            String taskType = (String) obj.get("taskType");
            if (!Const.STORM_CONFIG.DETECTION_TASK_FLAG.equals(taskType)) {
                continue;
            }
            TaskEntity ent = new TaskEntity();
            ent.setTaskId(obj.get("taskId").toString());
            ent.setTaskType(taskType);
            String videoIdListStr = obj.get("videoIdList").toString();
            Type videoIdListType = new TypeToken<List<String>>() {
            }.getType();
            List<String> videoIdList = (new Gson()).fromJson(videoIdListStr, videoIdListType);
            ent.setVideoIdList(videoIdList);
            ent.setStatus(obj.get("status").toString());
            result.add(ent);
            //            logger.info(ent.getTaskId() + "|" + ent.getTaskType() + "|" + ent.getStatus());
      // 将任务status置为1
            BasicDBObject newDocument = new BasicDBObject();
            newDocument.append("$set", new BasicDBObject().append("status", "1"));
            BasicDBObject updateQuery = new BasicDBObject().append("taskId", ent.getTaskId());
            col.update(updateQuery, newDocument);
        }
        cursor.close();
        return result;
    }

    public void insert(TaskEntity task) {
        if (null == task) {
            return;
        }
        DB db = mongoClient.getDB(Const.MONGO.MONGO_DATABASE);
        DBCollection col = db.getCollection(Const.MONGO.MONGO_TASK_COLLECTION);
        String jsonStr = (new Gson()).toJson(task);
        System.out.println(jsonStr);
        DBObject obj = (DBObject) JSON.parse(jsonStr);
        col.insert(obj);
    }

    /**
     * @Title: main 
     * @Description: TODO
     * @param @param args     
     * @return void   
     * @throws InterruptedException 
     * @throws 
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        //retrieval
        //        int count = 1;
        //        String[] videoIds = { "1", "815", "1412", "1847", "2200", "2604", "3387", "3752", "4304",
        //                "4542", "4847", "5229", "6125", "6545", "6653", "8449", "8653", "9310", "9811",
        //                "10381", "10580", "11047", "11465", "12818" };
        //        String[] videoIds = { "1" };
        //        for (int i = 0; i < videoIds.length; i++) {
        //            TaskDao dao = new TaskDao();
        //            String taskId = Integer.toString(count);
        //            String taskType = "retrieval";
        //            List<String> videoIdList = new ArrayList<String>();
        //            videoIdList.add(videoIds[i]);
        //            TaskEntity task = new TaskEntity();
        //            task.setTaskId(taskId);
        //            task.setTaskType(taskType);
        //            task.setVideoIdList(videoIdList);
        //            task.setStatus("0");
        //            task.setTimeStamp(Long.toString(System.currentTimeMillis()));
        //            dao.insert(task);
        //            count++;
        //        }
        //        for (int i = 0; i < videoIds.length; i++) {
        //            TaskDao dao = new TaskDao();
        //            String taskId = Integer.toString(count);
        //            String taskType = "retrieval";
        //            List<String> videoIdList = new ArrayList<String>();
        //            videoIdList.add(videoIds[i]);
        //            TaskEntity task = new TaskEntity();
        //            task.setTaskId(taskId);
        //            task.setTaskType(taskType);
        //            task.setVideoIdList(videoIdList);
        //            task.setStatus("0");
        //            task.setTimeStamp(Long.toString(System.currentTimeMillis()));
        //            dao.insert(task);
        //            count++;
        //        }

        //detection
        List<Integer> invalidVideoId = new ArrayList<Integer>();
        invalidVideoId.add(779);

        int minId = 1;
        int maxId = 100;

        List<String> videoIdList = new ArrayList<String>();
        for (int i = minId; i <= maxId; i++) {
            if (!invalidVideoId.contains(i)) {
                videoIdList.add(Integer.toString(i));
            }
        }

        int taskId = 860;

        for (int round = 1; round <= 20; round++) {
            for (int i = 1; i <= 10; i++) {
                TaskDao dao = new TaskDao();
                TaskEntity task = new TaskEntity();
                task.setTaskId(Integer.toString(taskId++));
                task.setTaskType("detection");
                task.setVideoIdList(videoIdList);
                task.setStatus("0");
                task.setTimeStamp(Long.toString(System.currentTimeMillis()));
                dao.insert(task);
            }
            Thread.sleep(1000);
        }
    }
}
