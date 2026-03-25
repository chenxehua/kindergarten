package com.kgms.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 课程安排DTO
 */
@Data
public class ScheduleDTO {

    /** 班级ID */
    @NotBlank(message = "班级ID不能为空")
    private String classId;

    /** 星期 */
    @NotNull(message = "星期不能为空")
    private Integer weekDay;

    /** 时间段 */
    @NotBlank(message = "时间段不能为空")
    private String timeSlot;

    /** 课程ID */
    @NotBlank(message = "课程ID不能为空")
    private String courseId;

    /** 教师ID */
    private String teacherId;
    /** 教室 */
    private String classroom;
}

/**
 * 课程安排VO
 */
@Data
class ScheduleVO {
    /** 安排ID */
    private String scheduleId;
    /** 班级ID */
    private String classId;
    /** 班级名称 */
    private String className;
    /** 星期 */
    private Integer weekDay;
    /** 时间段 */
    private String timeSlot;
    /** 课程ID */
    private String courseId;
    /** 课程名称 */
    private String courseName;
    /** 课程类型 */
    private String courseType;
    /** 教师ID */
    private String teacherId;
    /** 教师姓名 */
    private String teacherName;
    /** 教室 */
    private String classroom;
}