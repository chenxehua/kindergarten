package com.kgms.data.service;

import com.kgms.data.dto.AttendanceStatsDTO;
import com.kgms.data.entity.Attendance;
import com.kgms.data.mapper.AttendanceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AttendanceServiceTest {

    @Mock
    private AttendanceMapper attendanceMapper;

    @InjectMocks
    private AttendanceService attendanceService;

    private Attendance attendance;

    @BeforeEach
    void setUp() {
        attendance = new Attendance();
        attendance.setId(1L);
        attendance.setStudentId("student_001");
        attendance.setClassId("class_001");
        attendance.setAttendanceDate(LocalDate.now());
        attendance.setStatus(1); // 正常
        attendance.setCheckInTime(java.time.LocalDateTime.now());
        attendance.setCheckOutTime(java.time.LocalDateTime.now());
    }

    /**
     * TC-ATT-001: 考勤记录查询 - 成功
     */
    @Test
    void testGetAttendanceByDate_Success() {
        // Given
        String classId = "class_001";
        LocalDate date = LocalDate.now();
        List<Attendance> attendances = Arrays.asList(attendance);
        when(attendanceMapper.selectList(any())).thenReturn(attendances);

        // When
        List<Attendance> result = attendanceService.getAttendanceByDate(classId, date);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    /**
     * TC-ATT-001: 考勤记录查询 - 空数据
     */
    @Test
    void testGetAttendanceByDate_EmptyData() {
        // Given
        String classId = "class_001";
        LocalDate date = LocalDate.now();
        when(attendanceMapper.selectList(any())).thenReturn(Arrays.asList());

        // When
        List<Attendance> result = attendanceService.getAttendanceByDate(classId, date);

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    /**
     * TC-ATT-002: 考勤统计 - 成功
     */
    @Test
    void testGetAttendanceStats_Success() {
        // Given
        String classId = "class_001";
        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now();

        AttendanceStatsDTO stats = new AttendanceStatsDTO();
        stats.setTotalCount(100);
        stats.setNormalCount(95);
        stats.setAbsentCount(5);
        stats.setLeaveCount(0);
        stats.setAttendanceRate(95.0f);

        when(attendanceMapper.selectStats(eq(classId), any(), any())).thenReturn(stats);

        // When
        AttendanceStatsDTO result = attendanceService.getAttendanceStats(classId, startDate, endDate);

        // Then
        assertNotNull(result);
        assertEquals(100, result.getTotalCount());
        assertEquals(95.0f, result.getAttendanceRate());
    }
}