package com.kgms.data.service;

import com.kgms.data.dto.SchoolDashboardDTO;
import com.kgms.data.dto.ClassDashboardDTO;
import com.kgms.data.mapper.DashboardSnapshotMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private DashboardSnapshotMapper dashboardSnapshotMapper;

    @InjectMocks
    private DashboardService dashboardService;

    private SchoolDashboardDTO schoolDashboard;
    private ClassDashboardDTO classDashboard;

    @BeforeEach
    void setUp() {
        // 模拟校园数据
        schoolDashboard = new SchoolDashboardDTO();
        schoolDashboard.setTotalStudents(500);
        schoolDashboard.setTotalClasses(20);
        schoolDashboard.setTotalTeachers(50);
        schoolDashboard.setTodayAttendanceRate(98.5f);
        schoolDashboard.setTodayCheckInCount(490);
        schoolDashboard.setTodayCheckOutCount(480);
        schoolDashboard.setAvgHeight(115.5f);
        schoolDashboard.setAvgWeight(18.5f);

        // 模拟班级数据
        classDashboard = new ClassDashboardDTO();
        classDashboard.setClassId("class_001");
        classDashboard.setClassName("大一班");
        classDashboard.setStudentCount(25);
        classDashboard.setTodayAttendanceRate(100.0f);
        classDashboard.setCheckInCount(25);
    }

    /**
     * TC-DASH-001: 获取园区数据看板 - 成功
     */
    @Test
    void testGetSchoolDashboard_Success() {
        // Given
        String kgId = "kg_001";
        when(dashboardSnapshotMapper.selectSchoolDashboard(eq(kgId), any(LocalDate.class)))
                .thenReturn(schoolDashboard);

        // When
        SchoolDashboardDTO result = dashboardService.getSchoolDashboard(kgId);

        // Then
        assertNotNull(result);
        assertEquals(500, result.getTotalStudents());
        assertEquals(20, result.getTotalClasses());
        assertEquals(50, result.getTotalTeachers());
    }

    /**
     * TC-DASH-001: 获取园区数据看板 - 空数据
     */
    @Test
    void testGetSchoolDashboard_EmptyData() {
        // Given
        String kgId = "not_exist";
        when(dashboardSnapshotMapper.selectSchoolDashboard(eq(kgId), any(LocalDate.class)))
                .thenReturn(null);

        // When
        SchoolDashboardDTO result = dashboardService.getSchoolDashboard(kgId);

        // Then
        assertNull(result);
    }

    /**
     * TC-DASH-002: 获取班级数据看板 - 成功
     */
    @Test
    void testGetClassDashboard_Success() {
        // Given
        String classId = "class_001";
        when(dashboardSnapshotMapper.selectClassDashboard(eq(classId), any(LocalDate.class)))
                .thenReturn(classDashboard);

        // When
        ClassDashboardDTO result = dashboardService.getClassDashboard(classId);

        // Then
        assertNotNull(result);
        assertEquals("class_001", result.getClassId());
        assertEquals("大一班", result.getClassName());
        assertEquals(25, result.getStudentCount());
    }

    /**
     * TC-DASH-002: 获取班级数据看板 - 空数据
     */
    @Test
    void testGetClassDashboard_EmptyData() {
        // Given
        String classId = "not_exist";
        when(dashboardSnapshotMapper.selectClassDashboard(eq(classId), any(LocalDate.class)))
                .thenReturn(null);

        // When
        ClassDashboardDTO result = dashboardService.getClassDashboard(classId);

        // Then
        assertNull(result);
    }
}