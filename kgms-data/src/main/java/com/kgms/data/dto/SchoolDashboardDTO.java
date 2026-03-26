package com.kgms.data.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 园所看板数据
 */
@Data
public class SchoolDashboardDTO {

    // 运营概览
    private Integer totalStudents;
    private Integer totalClasses;
    private Integer totalTeachers;
    private Integer newStudentsThisMonth;
    private Integer leftStudentsThisMonth;

    // 全园考勤
    private Double attendanceRate;
    private List<Map<String, Object>> attendanceRanking;
    private List<Map<String, Object>> attendanceTrend;
    private Map<String, Integer> leaveReasonDistribution;

    // 数据统计
    private Integer totalRecordsThisMonth;
    private Integer totalPhotosThisMonth;
    private Integer totalVideosThisMonth;
    private Integer activeParents;
    private Integer messageCount;

    // 招生分析
    private Integer newEnrollments;
    private Double consultConversionRate;
    private Double renewalRate;
    private Double churnRate;

    // 运营指标
    private Double parentSatisfaction;
    private Double teacherWorkload;
    private Double courseCoverage;
    private Integer activityCount;
}
