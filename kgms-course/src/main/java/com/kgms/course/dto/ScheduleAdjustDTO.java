package com.kgms.course.dto;

import lombok.Data;

/**
 * 调课DTO
 */
@Data
public class ScheduleAdjustDTO {

    private String adjustId;
    private String scheduleId;
    private String classId;

    // 调整类型
    private String adjustType;
    private String originalDate;
    private String originalTime;
    private String adjustedDate;
    private String adjustedTime;

    // 代课信息
    private String originalTeacherId;
    private String substituteTeacherId;
    private String substituteReason;

    private String courseId;
    private String applyBy;
}
