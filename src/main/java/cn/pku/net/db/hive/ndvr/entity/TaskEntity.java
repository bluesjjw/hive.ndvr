/**
 * @Title: MongoTaskEntity.java 
 * @Package cn.pku.net.db.hive.ndvr.entity 
 * @Description: NDVR任务
 * @author Jiawei Jiang    
 * @date 2014年12月22日 下午3:48:40 
 * School of EECS, Peking University
 * Copyright (c) All Rights Reserved.
 */
package cn.pku.net.db.hive.ndvr.entity;

import java.util.List;

/**
 * @ClassName: MongoTaskEntity 
 * @Description: TODO
 * @author Jiawei Jiang
 * @date 2014年12月22日 下午3:48:40
 */
public class TaskEntity {

    private String       taskId;
    private String       taskType;   //retrieval or detection
    private List<String> videoIdList;
    private String       status;     //0:new, 1:done
    private String       timeStamp;  //时间戳

    /**
     * Getter method for property <tt>timeStamp</tt>.
     * 
     * @return property value of timeStamp
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * Setter method for property <tt>timeStamp</tt>.
     * 
     * @param timeStamp value to be assigned to property timeStamp
     */
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Getter method for property <tt>videoIdList</tt>.
     * 
     * @return property value of videoIdList
     */
    public List<String> getVideoIdList() {
        return videoIdList;
    }

    /**
     * Setter method for property <tt>videoIdList</tt>.
     * 
     * @param videoIdList value to be assigned to property videoIdList
     */
    public void setVideoIdList(List<String> videoIdList) {
        this.videoIdList = videoIdList;
    }

    /**
     * Getter method for property <tt>taskId</tt>.
     * 
     * @return property value of taskId
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * Setter method for property <tt>taskId</tt>.
     * 
     * @param taskId value to be assigned to property taskId
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * Getter method for property <tt>taskType</tt>.
     * 
     * @return property value of taskType
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * Setter method for property <tt>taskType</tt>.
     * 
     * @param taskType value to be assigned to property taskType
     */
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    /**
     * Getter method for property <tt>status</tt>.
     * 
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     * 
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
