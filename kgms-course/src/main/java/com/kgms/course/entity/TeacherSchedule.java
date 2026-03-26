package com.kgms.course.entity;

import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 老师课表安排实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TeacherSchedule extends BaseEntity {

    private String recordId;
    private String teacherId;
    private String scheduleId;
    private String classId;
    private String courseId;

    // 时间信息
    private String scheduleDate;
    private Integer weekDay;
    private Date startTime;
    private Date endTime;

    // 状态
    private String status;
    private String remark;
}
