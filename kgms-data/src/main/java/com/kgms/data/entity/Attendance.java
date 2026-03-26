package com.kgms.data.entity;

import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 考勤记录实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Attendance extends BaseEntity {

    private String attendanceId;
    private String studentId;
    private String classId;
    private String kgId;
    private String attendanceDate;

    // 签到签退
    private String checkInTime;
    private String checkInPhoto;
    private String checkOutTime;
    private String checkOutPhoto;

    // 考勤状态: NORMAL-正常 LATE-迟到 ABSENT-缺勤 LEAVE-请假
    private String status;
    private String leaveType;
    private String leaveReason;

    // 接送信息
    private String pickupPerson;
    private String pickupRelation;

    // 审批信息
    private String applyBy;
    private String approveBy;
    private Date approveTime;
    private String approveStatus;
}
