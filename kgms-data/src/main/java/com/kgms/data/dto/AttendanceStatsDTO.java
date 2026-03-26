package com.kgms.data.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 考勤统计
 */
@Data
public class AttendanceStatsDTO {

    // 统计周期
    private String startDate;
    private String endDate;

    // 班级考勤
    private String classId;
    private String className;
    private Double classAttendanceRate;

    // 出勤统计
    private Integer totalDays;
    private Integer presentDays;
    private Integer absentDays;
    private Integer leaveDays;
    private Integer lateDays;

    // 个人考勤
    private List<Map<String, Object>> studentAttendanceList;

    // 考勤趋势
    private List<Map<String, Object>> attendanceTrend;

    // 请假原因分布
    private Map<String, Integer> leaveReasonDistribution;
}
