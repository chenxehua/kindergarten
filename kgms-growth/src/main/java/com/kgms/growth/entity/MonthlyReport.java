package com.kgms.growth.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 月度报告实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_monthly_report")
public class MonthlyReport extends BaseEntity {
    /** 报告ID */
    private String reportId;
    /** 学生ID */
    private String studentId;
    /** 报告月份 */
    private String reportMonth;
    /** 出勤天数 */
    private Integer attendanceDays;
    /** 总天数 */
    private Integer totalDays;
    /** 亮点 */
    private String highlights;
    /** 维度数据 */
    private String dimensionData;
    /** AI总结 */
    private String aiSummary;
    /** 教师总结 */
    private String teacherSummary;
    /** 精选照片 */
    private String featuredPhotos;
    /** 状态 */
    private Integer status;
    /** 发布时间 */
    private LocalDateTime publishTime;
}
