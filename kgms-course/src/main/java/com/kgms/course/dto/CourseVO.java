package com.kgms.course.dto;

import lombok.Data;

/**
 * 课程VO
 */
@Data
public class CourseVO {
    /** 课程ID */
    private String courseId;
    /** 幼儿园ID */
    private String kgId;
    /** 课程名称 */
    private String courseName;
    /** 课程类型 */
    private String courseType;
    /** 适龄年龄段 */
    private String ageGroup;
    /** 授课教师ID */
    private String teacherId;
    /** 课程描述 */
    private String courseDesc;
    /** 课程目标 */
    private String courseGoal;
    /** 时长（分钟） */
    private Integer duration;
}
