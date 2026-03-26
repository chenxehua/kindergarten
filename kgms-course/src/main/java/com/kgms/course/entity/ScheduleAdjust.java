package com.kgms.course.entity;

import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 调课记录实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduleAdjust extends BaseEntity {

    private String adjustId;
    private String scheduleId;
    private String classId;

    // 调整类型: SWAP-换课 SUBSTITUTE-代课 MOVE-调时段
    private String adjustType;
    private String originalDate;
    private String originalTime;
    private String adjustedDate;
    private String adjustedTime;

    // 代课信息
    private String originalTeacherId;
    private String substituteTeacherId;
    private String substituteReason;

    // 课程信息
    private String courseId;

    // 状态
    private String status;
    private String applyBy;
    private String approveBy;
    private Date approveTime;
    private String approveRemark;
}
