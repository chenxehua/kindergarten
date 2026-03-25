package com.kgms.record.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RecordVO {
    private String recordId;
    private String studentId;
    private String studentName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate recordDate;
    private String teacherId;
    private String teacherName;
    private String classId;

    // 课程
    private String courseRecord;
    private String coursePhotos;

    // 情绪
    private String emotionType;
    private String emotionDetail;

    // 饮食
    private String breakfast;
    private String lunch;
    private String dinner;
    private String snack;
    private String foodPhotos;
    private Integer allergyFlag;
    private String allergyDetail;

    // 午休
    private String sleepTime;
    private String sleepQuality;
    private String sleepNote;

    // 活动
    private String activityType;
    private String activityDetail;
    private String activityPhotos;

    // 健康
    private String temperature;
    private String healthStatus;
    private String healthNote;

    // 总体
    private String overallNote;
    private Integer publishStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;
}
