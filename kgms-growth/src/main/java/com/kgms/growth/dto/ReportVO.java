package com.kgms.growth.dto;
import lombok.Data;
@Data
public class ReportVO {
    private String reportId;
    private String studentId;
    private String reportMonth;
    private Integer attendanceDays;
    private Integer totalDays;
    private String aiSummary;
    private String teacherSummary;
}
