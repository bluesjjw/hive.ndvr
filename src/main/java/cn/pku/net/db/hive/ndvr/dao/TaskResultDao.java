/**
 * @Title: TaskResultDao.java 
 * @Package cn.pku.net.db.hive.ndvr.dao 
 * @Description: TODO
 * @author Jiawei Jiang    
 * @date 2015年1月12日 下午1:32:49 
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
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

/**
 * @ClassName: TaskResultDao
 * @Description: TODO
 * @author Jiawei Jiang
 * @date 2015年1月12日 下午1:32:49
 */
public class TaskResultDao {

    private static final Logger logger      = Logger.getLogger(TaskResultDao.class);

    private static MongoClient  mongoClient = null;

    public TaskResultDao() {
        if (null == mongoClient) {
            try {
                mongoClient = new MongoClient(Const.MONGO.MONGO_TASK_HOST, Const.MONGO.MONGO_PORT);
            } catch (UnknownHostException e) {
                logger.error("MongoDB UnknownHost", e);
            }
        }
    }

    /**
   * @Title: getTaskResult
   * @Description: TODO
   * @param @return
   * @return MongoTaskEntity
   * @throws 如果没有任务，返回空数组
   */
    public List<TaskEntity> getTaskResult() {
        if (null == mongoClient) {
            return null;
        }
        List<TaskEntity> result = new ArrayList<TaskEntity>();
        DB db = mongoClient.getDB(Const.MONGO.MONGO_DATABASE);
        DBCollection col = db.getCollection(Const.MONGO.MONGO_TASK_RESULT_COLLECTION);
        DBCursor cursor = col.find();
        while (cursor.hasNext()) {
            DBObject obj = cursor.next();
            System.out.println(obj.toString());
            TaskEntity ent = new TaskEntity();
            ent.setTaskId(obj.get("taskId").toString());
            ent.setTaskType((String) obj.get("taskType"));
            ent.setTimeStamp((String) obj.get("timeStamp"));
            String videoIdListStr = obj.get("videoIdList").toString();
            Type videoIdListType = new TypeToken<List<String>>() {
            }.getType();
            List<String> videoIdList = (new Gson()).fromJson(videoIdListStr, videoIdListType);
            ent.setVideoIdList(videoIdList);
            ent.setStatus(obj.get("status").toString());
            result.add(ent);
        }
        cursor.close();
        return result;
    }

    public void insert(TaskEntity task) {
        if (null == task) {
            return;
        }
        DB db = mongoClient.getDB(Const.MONGO.MONGO_DATABASE);
        DBCollection col = db.getCollection(Const.MONGO.MONGO_TASK_RESULT_COLLECTION);
        String jsonStr = (new Gson()).toJson(task);
        //        System.out.println(jsonStr);
        DBObject obj = (DBObject) JSON.parse(jsonStr);
        col.insert(obj);
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
        TaskResultDao dao = new TaskResultDao();
        List<TaskEntity> result = dao.getTaskResult();
        System.out.println(result.size());
        for (TaskEntity ent : result) {
            //            System.out.println(ent.getTimeStamp());
        }
    }

}
