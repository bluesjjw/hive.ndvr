/**
 * @Title: VideoInfoEntity.java 
 * @Package cn.pku.net.db.hive.ndvr.entity 
 * @Description: TODO
 * @author Jiawei Jiang    
 * @date 2014年12月22日 下午8:25:26 
 * School of EECS, Peking University
 * Copyright (c) All Rights Reserved.
 */
package cn.pku.net.db.hive.ndvr.entity;

import java.util.Comparator;
import java.util.regex.Pattern;

/**
 * @ClassName: VideoInfoEntity
 * @Description: TODO
 * @author Jiawei Jiang
 * @date 2014年12月22日 下午8:25:26
 */
public class VideoInfoEntity implements Comparator<VideoInfoEntity> {

  private String videoId; // 视频id
  private String topicId; // 属于哪个检索词
  private String source; // 来自哪个网站:YouTube/Google Video/Yahoo Video
  private String videoFileName; // 视频文件名
  private int duration; // 视频时长,单位为秒
  private String format; // 视频文件格式
  private String title; // 视频标题
  private String url; // 视频url
  private String category; // 视频种类
  private String tags; // 标签
  private String description; // 描述
  private String author; // 作者

    public static VideoInfoEntity parse(String[] infos) {
        VideoInfoEntity ent = null;
        int length = infos.length;
        if (length >= 9) {
            ent = new VideoInfoEntity();
      // videoId和topicId必须为整形
            if (!isInteger(infos[0]) || !isInteger(infos[1])) {
                return ent;
            }
            ent.setVideoId(infos[0]);
            ent.setTopicId(infos[1]);
            ent.setSource(infos[2].substring(1, infos[2].length() - 1));
            ent.setVideoFileName(infos[3].substring(1, infos[3].length() - 1));
      // 如果数据duration字段不为空
            if (null != infos[4] && !infos[4].isEmpty()) {
                int flag = infos[4].indexOf(":");
                int duration = 60
                               * Integer.parseInt(infos[4].substring(1, flag))
                               + Integer.parseInt(infos[4].substring(flag + 1,
                                   infos[4].length() - 1));
                ent.setDuration(duration);
            }
      // 如果数据duration字段为空，则设为0
            else {
                ent.setDuration(0);
            }
            ent.setFormat(infos[5].substring(1, infos[5].length() - 1));
            ent.setTitle(infos[6].substring(1, infos[6].length() - 1));
            ent.setUrl(infos[8].substring(1, infos[8].length() - 1));
            //            ent.setCategory(infos[9].substring(1, infos[9].length() - 1));
            //            if (length >= 11) {
            //                ent.setTags(infos[10].substring(1, infos[10].length() - 1));
            //                if (length >= 12) {
            //                    ent.setDescription(infos[11].substring(1, infos[11].length() - 1));
            //                    if (length >= 13) {
            //                        ent.setAuthor(infos[12].substring(1, infos[12].length() - 1));
            //                    }
            //                }
            //
            //            }
        }
        return ent;
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * Getter method for property <tt>videoId</tt>.
     * 
     * @return property value of videoId
     */
    public String getVideoId() {
        return videoId;
    }

    /**
     * Setter method for property <tt>videoId</tt>.
     * 
     * @param videoId value to be assigned to property videoId
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    /**
     * Getter method for property <tt>topicId</tt>.
     * 
     * @return property value of topicId
     */
    public String getTopicId() {
        return topicId;
    }

    /**
     * Setter method for property <tt>topicId</tt>.
     * 
     * @param topicId value to be assigned to property topicId
     */
    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    /**
     * Getter method for property <tt>source</tt>.
     * 
     * @return property value of source
     */
    public String getSource() {
        return source;
    }

    /**
     * Setter method for property <tt>source</tt>.
     * 
     * @param source value to be assigned to property source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Getter method for property <tt>videoFileName</tt>.
     * 
     * @return property value of videoFileName
     */
    public String getVideoFileName() {
        return videoFileName;
    }

    /**
     * Setter method for property <tt>videoFileName</tt>.
     * 
     * @param videoFileName value to be assigned to property videoFileName
     */
    public void setVideoFileName(String videoFileName) {
        this.videoFileName = videoFileName;
    }

    /**
     * Getter method for property <tt>duration</tt>.
     * 
     * @return property value of duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Setter method for property <tt>duration</tt>.
     * 
     * @param duration value to be assigned to property duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Getter method for property <tt>format</tt>.
     * 
     * @return property value of format
     */
    public String getFormat() {
        return format;
    }

    /**
     * Setter method for property <tt>format</tt>.
     * 
     * @param format value to be assigned to property format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Getter method for property <tt>title</tt>.
     * 
     * @return property value of title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter method for property <tt>title</tt>.
     * 
     * @param title value to be assigned to property title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter method for property <tt>url</tt>.
     * 
     * @return property value of url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter method for property <tt>url</tt>.
     * 
     * @param url value to be assigned to property url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Getter method for property <tt>category</tt>.
     * 
     * @return property value of category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Setter method for property <tt>category</tt>.
     * 
     * @param category value to be assigned to property category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Getter method for property <tt>tags</tt>.
     * 
     * @return property value of tags
     */
    public String getTags() {
        return tags;
    }

    /**
     * Setter method for property <tt>tags</tt>.
     * 
     * @param tags value to be assigned to property tags
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * Getter method for property <tt>description</tt>.
     * 
     * @return property value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method for property <tt>description</tt>.
     * 
     * @param description value to be assigned to property description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter method for property <tt>author</tt>.
     * 
     * @return property value of author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Setter method for property <tt>author</tt>.
     * 
     * @param author value to be assigned to property author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /** 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(VideoInfoEntity arg0, VideoInfoEntity arg1) {
        if (arg0.getDuration() < arg1.getDuration()) {
            return -1;
        } else if (arg0.getDuration() > arg1.getDuration()) {
            return 1;
        }

        return 0;
    }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("VideoId:" + this.videoId);
    sb.append(", TopicId:" + this.topicId);
    sb.append(", Source:" + this.source);
    sb.append(", VideoFileName:" + this.videoFileName);
    sb.append(", Duration:" + this.duration);
    sb.append(", Format:" + this.format);
    sb.append(", Title:" + this.title);
    sb.append(", Url:" + this.url);
    return sb.toString();
  }

}
