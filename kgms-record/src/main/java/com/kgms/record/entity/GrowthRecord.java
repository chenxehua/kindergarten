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
    private String courseRecord;
    private String coursePhotos;
    private String emotionType;
    private String emotionDetail;
    private String breakfast;
    private String lunch;
    private String dinner;
    private String snack;
    private String foodPhotos;
    private Integer allergyFlag;
    private String allergyDetail;
    private String sleepTime;
    private String sleepQuality;
    private String sleepNote;
    private String activityType;
    private String activityDetail;
    private String activityPhotos;
    private BigDecimal temperature;
    private String healthStatus;
    private String healthNote;
    private String overallNote;
    private Integer publishStatus;
    private LocalDateTime publishTime;
}
