package com.kgms.record.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 成长记录实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_growth_record")
public class GrowthRecord extends BaseEntity {
    /** 记录ID */
    private String recordId;
    /** 学生ID */
    private String studentId;
    /** 记录日期 */
    private LocalDate recordDate;
    /** 教师ID */
    private String teacherId;
    /** 班级ID */
    private String classId;
    /** 课程记录 */
    private String courseRecord;
    /** 课程照片 */
    private String coursePhotos;
    /** 情绪类型 */
    private String emotionType;
    /** 情绪详情 */
    private String emotionDetail;
    /** 早餐 */
    private String breakfast;
    /** 午餐 */
    private String lunch;
    /** 晚餐 */
    private String dinner;
    /** 点心 */
    private String snack;
    /** 食物照片 */
    private String foodPhotos;
    /** 过敏标志 */
    private Integer allergyFlag;
    /** 过敏详情 */
    private String allergyDetail;
    /** 睡眠时间 */
    private String sleepTime;
    /** 睡眠质量 */
    private String sleepQuality;
    /** 睡眠备注 */
    private String sleepNote;
    /** 活动类型 */
    private String activityType;
    /** 活动详情 */
    private String activityDetail;
    /** 活动照片 */
    private String activityPhotos;
    /** 体温 */
    private BigDecimal temperature;
    /** 健康状态 */
    private String healthStatus;
    /** 健康备注 */
    private String healthNote;
    /** 总体评价 */
    private String overallNote;
    /** 发布状态 */
    private Integer publishStatus;
    /** 发布时间 */
    private LocalDateTime publishTime;
}
