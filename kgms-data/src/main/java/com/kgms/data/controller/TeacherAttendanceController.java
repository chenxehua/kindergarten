package com.kgms.data.controller;

import com.kgms.common.result.Result;
import com.kgms.data.entity.TeacherAttendance;
import com.kgms.data.service.TeacherAttendanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/teacher-attendance")
@Slf4j
public class TeacherAttendanceController {

    @Autowired
    private TeacherAttendanceService teacherAttendanceService;

    /**
     * 老师签到
     */
    @PostMapping("/check-in")
    public Result<TeacherAttendance> checkIn(
            @RequestParam String teacherId,
            @RequestParam(required = false) String location) {
        return Result.success(teacherAttendanceService.checkIn(teacherId, location));
    }

    /**
     * 老师签退
     */
    @PostMapping("/check-out")
    public Result<TeacherAttendance> checkOut(
            @RequestParam String teacherId,
            @RequestParam(required = false) String location) {
        return Result.success(teacherAttendanceService.checkOut(teacherId, location));
    }

    /**
     * 老师请假
     */
    @PostMapping("/leave")
    public Result<TeacherAttendance> applyLeave(
            @RequestParam String teacherId,
            @RequestParam String leaveType,
            @RequestParam String leaveReason) {
        return Result.success(teacherAttendanceService.applyLeave(teacherId, leaveType, leaveReason));
    }

    /**
     * 获取老师考勤记录
     */
    @GetMapping("/records")
    public Result<List<TeacherAttendance>> getTeacherAttendance(
            @RequestParam String teacherId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return Result.success(teacherAttendanceService.getTeacherAttendance(teacherId, startDate, endDate));
    }

    /**
     * 获取老师考勤统计
     */
    @GetMapping("/stats")
    public Result<Map<String, Object>> getTeacherAttendanceStats(
            @RequestParam String teacherId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return Result.success(teacherAttendanceService.getTeacherAttendanceStats(teacherId, startDate, endDate));
    }
}
