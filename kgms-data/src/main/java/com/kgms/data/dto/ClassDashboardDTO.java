package com.kgms.data.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 班级看板数据
 */
@Data
public class ClassDashboardDTO {

    // 班级基本信息
    private String classId;
    private String className;
    private String grade;

    // 人数统计
    private Integer totalStudents;
    private Integer boyCount;
    private Integer girlCount;

    // 今日考勤
    private Integer presentCount;
    private Integer absentCount;
    private Integer leaveCount;
    private Integer lateCount;
    private Double attendanceRate;

    // 本周考勤趋势
    private List<Map<String, Object>> attendanceTrend;

    // 课程安排
    private List<Map<String, Object>> todaySchedule;

    // 成长记录统计
    private Integer todayRecordCount;
    private Integer weekRecordCount;
    private Integer monthRecordCount;
    private Integer photoCount;

    // 家长互动
    private Integer messageCount;
    private Double replyRate;
    private List<Map<String, Object>> activeParents;

    // 需要关注的学生
    private List<Map<String, Object>> attentionStudents;
}
