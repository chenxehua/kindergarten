package com.kgms.growth.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_monthly_report")
public class MonthlyReport extends BaseEntity {
    private String reportId;
    private String studentId;
    private String reportMonth;
    private Integer attendanceDays;
    private Integer totalDays;
    private String highlights;
    private String dimensionData;
    private String aiSummary;
    private String teacherSummary;
    private String featuredPhotos;
    private Integer status;
    private LocalDateTime publishTime;
}
