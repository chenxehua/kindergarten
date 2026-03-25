package com.kgms.course.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_class_schedule")
public class ClassSchedule extends BaseEntity {

    private String scheduleId;
    private String classId;
    private Integer weekDay;      // 1-7
    private String timeSlot;     // 上午/中午/下午
    private String courseId;
    private String teacherId;
    private String classroom;
}