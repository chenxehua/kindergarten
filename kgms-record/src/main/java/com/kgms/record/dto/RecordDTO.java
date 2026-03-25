package com.kgms.record.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RecordDTO {

    @NotBlank(message = "学生ID不能为空")
    private String studentId;

    @NotNull(message = "记录日期不能为空")
    private LocalDate recordDate;

    // 课程记录
    private String courseRecord;
    private String coursePhotos;

    // 情绪记录
    private String emotionType;
    private String emotionDetail;

    // 饮食记录
    private String breakfast;
    private String lunch;
    private String dinner;
    private String snack;
    private String foodPhotos;
    private Integer allergyFlag;
    private String allergyDetail;

    // 午休记录
    private String sleepTime;
    private String sleepQuality;
    private String sleepNote;

    // 活动记录
    private String activityType;
    private String activityDetail;
    private String activityPhotos;

    // 健康记录
    private String temperature;
    private String healthStatus;
    private String healthNote;

    // 总体评价
    private String overallNote;
}
