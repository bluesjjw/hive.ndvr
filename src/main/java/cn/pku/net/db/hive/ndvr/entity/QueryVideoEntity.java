/**
 * @Title: QueryVideoEntity.java 
 * @Package cn.pku.net.db.hive.ndvr.entity 
 * @Description: TODO
 * @author Jiawei Jiang    
 * @date 2014年12月22日 下午10:32:42 
 * School of EECS, Peking University
 * Copyright (c) All Rights Reserved.
 */
package cn.pku.net.db.hive.ndvr.entity;

import java.util.List;

/**
 * @ClassName: QueryVideoEntity 
 * @Description: TODO
 * @author Jiawei Jiang
 * @date 2014年12月22日 下午10:32:42
 */
public class QueryVideoEntity {

    private String       taskId;
    private List<String> videoIdList;

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

}
