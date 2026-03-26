package com.kgms.data.service;

import com.kgms.data.dto.ClassDashboardDTO;
import com.kgms.data.dto.SchoolDashboardDTO;
import com.kgms.data.dto.GrowthDashboardDTO;
import com.kgms.data.entity.DashboardSnapshot;
import com.kgms.data.mapper.DashboardSnapshotMapper;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private DashboardSnapshotMapper dashboardSnapshotMapper;

    @InjectMocks
    private DashboardService dashboardService;

    private DashboardSnapshot testSnapshot;

    @BeforeEach
    void setUp() {
        testSnapshot = new DashboardSnapshot();
        testSnapshot.setId(1L);
        testSnapshot.setKgId("kg_001");
        testSnapshot.setSnapshotDate(LocalDate.now().toString());
        testSnapshot.setSnapshotType("DAILY");
    }

    /**
     * TC-DASH-001: 获取班级看板数据
     */
    @Test
    void testGetClassDashboard() {
        // When - 直接使用内存数据，不需要mock
        ClassDashboardDTO result = dashboardService.getClassDashboard("class_001");

        // Then
        assertNotNull(result);
        assertEquals("class_001", result.getClassId());
    }

    /**
     * TC-DASH-002: 获取园所看板数据
     */
    @Test
    void testGetSchoolDashboard() {
        // When
        SchoolDashboardDTO result = dashboardService.getSchoolDashboard("kg_001");

        // Then
        assertNotNull(result);
        assertNotNull(result.getTotalStudents());
        assertNotNull(result.getTotalClasses());
    }

    /**
     * TC-DASH-003: 获取成长看板数据
     */
    @Test
    void testGetGrowthDashboard() {
        // When
        GrowthDashboardDTO result = dashboardService.getGrowthDashboard("kg_001");

        // Then
        assertNotNull(result);
    }

    /**
     * TC-DASH-004: 生成每日快照
     */
    @Test
    void testGenerateDailySnapshot() {
        // Given
        when(dashboardSnapshotMapper.insert(any())).thenReturn(1);

        // When & Then - 不抛异常即成功
        assertDoesNotThrow(() -> dashboardService.generateDailySnapshot("kg_001"));
    }

    /**
     * TC-DASH-005: 获取快照历史
     */
    @Test
    void testGetSnapshotHistory() {
        // Given
        when(dashboardSnapshotMapper.selectByKgIdAndDateRange(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Arrays.asList(testSnapshot));

        // When
        List<DashboardSnapshot> result = dashboardService.getSnapshotHistory(
            "kg_001", "2026-01-01", "2026-03-26", "DAILY");

        // Then
        assertNotNull(result);
    }
}
