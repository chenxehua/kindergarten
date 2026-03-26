package com.kgms.data.service;

import com.kgms.common.util.IdGenerator;
import com.kgms.data.dto.AttendanceStatsDTO;
import com.kgms.data.entity.Attendance;
import com.kgms.data.mapper.AttendanceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class AttendanceService {

    @Autowired
    private AttendanceMapper attendanceMapper;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 学生签到
     */
    public Attendance checkIn(String studentId, String classId, String kgId, String checkInPhoto) {
        String attendanceDate = LocalDate.now().format(dateFormatter);

        // 检查是否已有签到记录
        List<Attendance> existingRecords = attendanceMapper.selectByClassIdAndDate(classId, attendanceDate);
        Optional<Attendance> existing = existingRecords.stream()
                .filter(r -> r.getStudentId().equals(studentId))
                .findFirst();

        if (existing.isPresent()) {
            Attendance record = existing.get();
            if (record.getCheckInTime() != null) {
                throw new RuntimeException("今日已签到");
            }
            // 更新签到时间
            record.setCheckInTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
            record.setCheckInPhoto(checkInPhoto);
            attendanceMapper.updateByAttendanceId(record);
            return record;
        }

        // 创建新签到记录
        Attendance attendance = new Attendance();
        attendance.setAttendanceId(IdGenerator.generateId("ATT"));
        attendance.setStudentId(studentId);
        attendance.setClassId(classId);
        attendance.setKgId(kgId);
        attendance.setAttendanceDate(attendanceDate);
        attendance.setCheckInTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        attendance.setCheckInPhoto(checkInPhoto);
        attendance.setStatus("NORMAL");
        attendance.setApproveStatus("APPROVED");

        // 检查是否迟到（9点后签到算迟到）
        LocalTime checkInTime = LocalTime.now();
        LocalTime lateTime = LocalTime.of(9, 0);
        if (checkInTime.isAfter(lateTime)) {
            attendance.setStatus("LATE");
        }

        attendanceMapper.insert(attendance);
        log.info("Student {} checked in at {}", studentId, attendance.getCheckInTime());
        return attendance;
    }

    /**
     * 学生签退
     */
    public Attendance checkOut(String studentId, String pickupPerson, String pickupRelation) {
        String attendanceDate = LocalDate.now().format(dateFormatter);

        // 查询当天的签到记录
        List<Attendance> existingRecords = attendanceMapper.selectByClassIdAndDate(
                null, attendanceDate);
        Optional<Attendance> existing = existingRecords.stream()
                .filter(r -> r.getStudentId().equals(studentId))
                .findFirst();

        if (!existing.isPresent()) {
            throw new RuntimeException("未找到签到记录");
        }

        Attendance record = existing.get();
        if (record.getCheckOutTime() != null) {
            throw new RuntimeException("今日已签退");
        }

        record.setCheckOutTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        record.setPickupPerson(pickupPerson);
        record.setPickupRelation(pickupRelation);

        attendanceMapper.updateByAttendanceId(record);
        log.info("Student {} checked out at {}", studentId, record.getCheckOutTime());
        return record;
    }

    /**
     * 提交请假申请
     */
    public Attendance applyLeave(String studentId, String classId, String kgId,
                                 String leaveType, String leaveReason, String applyBy) {
        String attendanceDate = LocalDate.now().format(dateFormatter);

        Attendance attendance = new Attendance();
        attendance.setAttendanceId(IdGenerator.generateId("ATT"));
        attendance.setStudentId(studentId);
        attendance.setClassId(classId);
        attendance.setKgId(kgId);
        attendance.setAttendanceDate(attendanceDate);
        attendance.setStatus("LEAVE");
        attendance.setLeaveType(leaveType);
        attendance.setLeaveReason(leaveReason);
        attendance.setApplyBy(applyBy);
        attendance.setApproveStatus("PENDING");

        attendanceMapper.insert(attendance);
        log.info("Leave application submitted for student {}", studentId);
        return attendance;
    }

    /**
     * 审批请假
     */
    public Attendance approveLeave(String attendanceId, String approveBy, boolean approved, String remark) {
        Attendance attendance = attendanceMapper.selectByAttendanceId(attendanceId);
        if (attendance == null) {
            throw new RuntimeException("考勤记录不存在");
        }

        attendance.setApproveBy(approveBy);
        attendance.setApproveTime(new Date());
        attendance.setApproveStatus(approved ? "APPROVED" : "REJECTED");

        if (!approved && remark != null) {
            attendance.setLeaveReason(attendance.getLeaveReason() + "\n驳回原因: " + remark);
        }

        attendanceMapper.updateByAttendanceId(attendance);
        log.info("Leave application {} {}", attendanceId, approved ? "approved" : "rejected");
        return attendance;
    }

    /**
     * 获取班级考勤记录
     */
    public List<Attendance> getClassAttendance(String classId, String date) {
        if (date == null) {
            date = LocalDate.now().format(dateFormatter);
        }
        return attendanceMapper.selectByClassIdAndDate(classId, date);
    }

    /**
     * 获取学生考勤统计
     */
    public AttendanceStatsDTO getStudentAttendanceStats(String studentId, String startDate, String endDate) {
        List<Attendance> records = attendanceMapper.selectByStudentIdAndDateRange(studentId, startDate, endDate);

        AttendanceStatsDTO stats = new AttendanceStatsDTO();
        stats.setStartDate(startDate);
        stats.setEndDate(endDate);

        // 统计
        int totalDays = records.size();
        long presentDays = records.stream().filter(r -> "NORMAL".equals(r.getStatus())).count();
        long absentDays = records.stream().filter(r -> "ABSENT".equals(r.getStatus())).count();
        long leaveDays = records.stream().filter(r -> "LEAVE".equals(r.getStatus())).count();
        long lateDays = records.stream().filter(r -> "LATE".equals(r.getStatus())).count();

        stats.setTotalDays(totalDays);
        stats.setPresentDays((int) presentDays);
        stats.setAbsentDays((int) absentDays);
        stats.setLeaveDays((int) leaveDays);
        stats.setLateDays((int) lateDays);

        // 计算出勤率
        if (totalDays > 0) {
            double rate = (double) presentDays / totalDays * 100;
            stats.setClassAttendanceRate(Math.round(rate * 100.0) / 100.0);
        }

        return stats;
    }

    /**
     * 获取班级考勤统计
     */
    public AttendanceStatsDTO getClassAttendanceStats(String classId, String startDate, String endDate) {
        List<Map<String, Object>> studentStats = attendanceMapper.selectAttendanceStats(classId, startDate, endDate);

        AttendanceStatsDTO stats = new AttendanceStatsDTO();
        stats.setStartDate(startDate);
        stats.setEndDate(endDate);
        stats.setClassId(classId);

        // 汇总统计
        int totalStudents = studentStats.size();
        int totalDays = 0;
        int presentDays = 0;
        int absentDays = 0;
        int leaveDays = 0;
        int lateDays = 0;

        for (Map<String, Object> studentStat : studentStats) {
            totalDays += ((Long) studentStat.get("total_days")).intValue();
            presentDays += ((Long) studentStat.get("present_days")).intValue();
            absentDays += ((Long) studentStat.get("absent_days")).intValue();
            leaveDays += ((Long) studentStat.get("leave_days")).intValue();
            lateDays += ((Long) studentStat.get("late_days")).intValue();
        }

        stats.setTotalDays(totalDays);
        stats.setPresentDays(presentDays);
        stats.setAbsentDays(absentDays);
        stats.setLeaveDays(leaveDays);
        stats.setLateDays(lateDays);

        if (totalDays > 0) {
            double rate = (double) presentDays / totalDays * 100;
            stats.setClassAttendanceRate(Math.round(rate * 100.0) / 100.0);
        }

        // 学生考勤列表
        List<Map<String, Object>> studentList = new ArrayList<>();
        for (Map<String, Object> studentStat : studentStats) {
            Map<String, Object> item = new HashMap<>();
            item.put("studentId", studentStat.get("student_id"));
            item.put("totalDays", studentStat.get("total_days"));
            item.put("presentDays", studentStat.get("present_days"));
            item.put("absentDays", studentStat.get("absent_days"));
            item.put("leaveDays", studentStat.get("leave_days"));
            item.put("lateDays", studentStat.get("late_days"));
            studentList.add(item);
        }
        stats.setStudentAttendanceList(studentList);

        return stats;
    }
}
