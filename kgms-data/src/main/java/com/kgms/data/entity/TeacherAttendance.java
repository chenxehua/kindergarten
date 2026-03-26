package com.kgms.data.entity;

import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 老师考勤记录实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TeacherAttendance extends BaseEntity {

    private String recordId;
    private String teacherId;
    private String attendanceDate;

    // 签到签退
    private String checkInTime;
    private String checkInLocation;
    private String checkOutTime;
    private String checkOutLocation;

    // 考勤状态
    private String status;
    private String leaveType;
    private String leaveReason;
    private String leaveProof;
}
