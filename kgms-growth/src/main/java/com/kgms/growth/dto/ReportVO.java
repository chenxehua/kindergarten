package com.kgms.growth.dto;
import lombok.Data;

/**
 * 月度报告VO
 */
@Data
public class ReportVO {
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
    /** AI总结 */
    private String aiSummary;
    /** 教师总结 */
    private String teacherSummary;
}
