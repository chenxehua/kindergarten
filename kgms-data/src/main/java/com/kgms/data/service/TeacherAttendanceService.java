package com.kgms.data.service;

import com.kgms.common.util.IdGenerator;
import com.kgms.data.entity.TeacherAttendance;
import com.kgms.data.mapper.TeacherAttendanceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TeacherAttendanceService {

    @Autowired
    private TeacherAttendanceMapper teacherAttendanceMapper;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 老师签到
     */
    public TeacherAttendance checkIn(String teacherId, String location) {
        String attendanceDate = LocalDate.now().format(dateFormatter);

        // 检查是否已签到
        TeacherAttendance existing = teacherAttendanceMapper.selectByTeacherAndDate(teacherId, attendanceDate);
        if (existing != null) {
            if (existing.getCheckInTime() != null) {
                throw new RuntimeException("今日已签到");
            }
            // 更新签到
            existing.setCheckInTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
            existing.setCheckInLocation(location);
            teacherAttendanceMapper.updateByRecordId(existing);
            return existing;
        }

        // 新建签到记录
        TeacherAttendance attendance = new TeacherAttendance();
        attendance.setRecordId(IdGenerator.generateIdWithPrefix("TAT"));
        attendance.setTeacherId(teacherId);
        attendance.setAttendanceDate(attendanceDate);
        attendance.setCheckInTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        attendance.setCheckInLocation(location);
        attendance.setStatus("NORMAL");

        // 检查是否迟到（8:30后签到算迟到）
        LocalTime checkInTime = LocalTime.now();
        LocalTime lateTime = LocalTime.of(8, 30);
        if (checkInTime.isAfter(lateTime)) {
            attendance.setStatus("LATE");
        }

        teacherAttendanceMapper.insert(attendance);
        log.info("Teacher {} checked in at {}", teacherId, attendance.getCheckInTime());
        return attendance;
    }

    /**
     * 老师签退
     */
    public TeacherAttendance checkOut(String teacherId, String location) {
        String attendanceDate = LocalDate.now().format(dateFormatter);

        TeacherAttendance attendance = teacherAttendanceMapper.selectByTeacherAndDate(teacherId, attendanceDate);
        if (attendance == null) {
            throw new RuntimeException("未找到签到记录");
        }

        if (attendance.getCheckOutTime() != null) {
            throw new RuntimeException("今日已签退");
        }

        attendance.setCheckOutTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        attendance.setCheckOutLocation(location);

        teacherAttendanceMapper.updateByRecordId(attendance);
        log.info("Teacher {} checked out at {}", teacherId, attendance.getCheckOutTime());
        return attendance;
    }

    /**
     * 老师请假
     */
    public TeacherAttendance applyLeave(String teacherId, String leaveType, String leaveReason) {
        String attendanceDate = LocalDate.now().format(dateFormatter);

        TeacherAttendance attendance = new TeacherAttendance();
        attendance.setRecordId(IdGenerator.generateIdWithPrefix("TAT"));
        attendance.setTeacherId(teacherId);
        attendance.setAttendanceDate(attendanceDate);
        attendance.setStatus("LEAVE");
        attendance.setLeaveType(leaveType);
        attendance.setLeaveReason(leaveReason);

        teacherAttendanceMapper.insert(attendance);
        log.info("Teacher {} applied for leave: {}", teacherId, leaveType);
        return attendance;
    }

    /**
     * 获取老师考勤记录
     */
    public List<TeacherAttendance> getTeacherAttendance(String teacherId, String startDate, String endDate) {
        return teacherAttendanceMapper.selectByTeacherAndDateRange(teacherId, startDate, endDate);
    }

    /**
     * 获取老师考勤统计
     */
    public Map<String, Object> getTeacherAttendanceStats(String teacherId, String startDate, String endDate) {
        List<TeacherAttendance> records = teacherAttendanceMapper.selectByTeacherAndDateRange(teacherId, startDate, endDate);

        Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("totalDays", records.size());
        stats.put("normalDays", records.stream().filter(r -> "NORMAL".equals(r.getStatus())).count());
        stats.put("lateDays", records.stream().filter(r -> "LATE".equals(r.getStatus())).count());
        stats.put("leaveDays", records.stream().filter(r -> "LEAVE".equals(r.getStatus())).count());

        return stats;
    }
}
