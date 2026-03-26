package com.kgms.data.controller;

import com.kgms.common.result.Result;
import com.kgms.data.dto.AttendanceStatsDTO;
import com.kgms.data.entity.Attendance;
import com.kgms.data.service.AttendanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@Slf4j
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    /**
     * 学生签到
     */
    @PostMapping("/check-in")
    public Result<Attendance> checkIn(
            @RequestParam String studentId,
            @RequestParam String classId,
            @RequestParam String kgId,
            @RequestParam(required = false) String checkInPhoto) {
        return Result.success(attendanceService.checkIn(studentId, classId, kgId, checkInPhoto));
    }

    /**
     * 学生签退
     */
    @PostMapping("/check-out")
    public Result<Attendance> checkOut(
            @RequestParam String studentId,
            @RequestParam String pickupPerson,
            @RequestParam String pickupRelation) {
        return Result.success(attendanceService.checkOut(studentId, pickupPerson, pickupRelation));
    }

    /**
     * 提交请假申请
     */
    @PostMapping("/leave")
    public Result<Attendance> applyLeave(
            @RequestParam String studentId,
            @RequestParam String classId,
            @RequestParam String kgId,
            @RequestParam String leaveType,
            @RequestParam String leaveReason,
            @RequestParam String applyBy) {
        return Result.success(attendanceService.applyLeave(studentId, classId, kgId, leaveType, leaveReason, applyBy));
    }

    /**
     * 审批请假
     */
    @PostMapping("/leave/{attendanceId}/approve")
    public Result<Attendance> approveLeave(
            @PathVariable String attendanceId,
            @RequestParam String approveBy,
            @RequestParam boolean approved,
            @RequestParam(required = false) String remark) {
        return Result.success(attendanceService.approveLeave(attendanceId, approveBy, approved, remark));
    }

    /**
     * 获取班级考勤记录
     */
    @GetMapping("/class")
    public Result<List<Attendance>> getClassAttendance(
            @RequestParam String classId,
            @RequestParam(required = false) String date) {
        return Result.success(attendanceService.getClassAttendance(classId, date));
    }

    /**
     * 获取学生考勤统计
     */
    @GetMapping("/student/stats")
    public Result<AttendanceStatsDTO> getStudentAttendanceStats(
            @RequestParam String studentId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return Result.success(attendanceService.getStudentAttendanceStats(studentId, startDate, endDate));
    }

    /**
     * 获取班级考勤统计
     */
    @GetMapping("/class/stats")
    public Result<AttendanceStatsDTO> getClassAttendanceStats(
            @RequestParam String classId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return Result.success(attendanceService.getClassAttendanceStats(classId, startDate, endDate));
    }
}
