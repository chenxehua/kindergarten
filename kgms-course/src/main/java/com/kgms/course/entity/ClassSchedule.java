package com.kgms.course.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课程安排实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_class_schedule")
public class ClassSchedule extends BaseEntity {

    /** 安排ID */
    private String scheduleId;
    /** 班级ID */
    private String classId;
    /** 星期（1-7） */
    private Integer weekDay;
    /** 时间段 */
    private String timeSlot;
    /** 课程ID */
    private String courseId;
    /** 教师ID */
    private String teacherId;
    /** 教室 */
    private String classroom;
}