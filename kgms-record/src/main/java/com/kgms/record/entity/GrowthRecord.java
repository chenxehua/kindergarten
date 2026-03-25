package com.kgms.record.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_growth_record")
public class GrowthRecord extends BaseEntity {

    private String recordId;
    private String studentId;
    private LocalDate recordDate;
    private String teacherId;
    private String classId;

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
    private BigDecimal temperature;
    private String healthStatus;
    private String healthNote;

    // 总体评价
    private String overallNote;
    private Integer publishStatus;
    private LocalDateTime publishTime;
}
