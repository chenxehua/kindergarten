package com.kgms.course.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课程实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_course")
public class Course extends BaseEntity {
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
    /** 状态 */
    private Integer status;
}
