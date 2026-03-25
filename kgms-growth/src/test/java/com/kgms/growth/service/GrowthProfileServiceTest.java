package com.kgms.growth.service;

import com.kgms.growth.dto.ProfileVO;
import com.kgms.growth.dto.ReportVO;
import com.kgms.growth.entity.GrowthProfile;
import com.kgms.growth.entity.MonthlyReport;
import com.kgms.growth.mapper.GrowthProfileMapper;
import com.kgms.growth.mapper.MonthlyReportMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GrowthProfileServiceTest {

    @Mock
    private GrowthProfileMapper profileMapper;

    @Mock
    private MonthlyReportMapper reportMapper;

    @InjectMocks
    private GrowthProfileService growthProfileService;

    private GrowthProfile testProfile;
    private MonthlyReport testReport;

    @BeforeEach
    void setUp() {
        // 初始化测试数据
        testProfile = new GrowthProfile();
        testProfile.setId(1L);
        testProfile.setProfileId("profile_001");
        testProfile.setStudentId("stu_001");
        testProfile.setProfileMonth("2026-03");
        testProfile.setEmotionScore(85);
        testProfile.setSocialScore(80);
        testProfile.setLearningScore(88);
        testProfile.setSportScore(82);
        testProfile.setDietScore(90);
        testProfile.setOverallScore(85);
        testProfile.setAiAnalysis("本月表现优秀，情绪稳定");

        testReport = new MonthlyReport();
        testReport.setId(1L);
        testReport.setReportId("report_001");
        testReport.setStudentId("stu_001");
        testReport.setReportMonth("2026-03");
        testReport.setAttendanceDays(20);
        testReport.setTotalDays(22);
        testReport.setAiSummary("本月表现优秀");
        testReport.setTeacherSummary("继续加油！");
        testReport.setStatus(1);
    }

    /**
     * TC-AI-001: 生成AI成长画像
     */
    @Test
    void testGenerateProfile_NewProfile() {
        // Given
        when(profileMapper.selectOne(any())).thenReturn(null);
        when(profileMapper.insert(any(GrowthProfile.class))).thenReturn(1);

        // When
        String profileId = growthProfileService.generateProfile("stu_001", "2026-03");

        // Then
        assertNotNull(profileId);
        verify(profileMapper, times(1)).insert(any(GrowthProfile.class));
    }

    /**
     * TC-AI-001: 生成AI成长画像 - 已存在
     */
    @Test
    void testGenerateProfile_AlreadyExists() {
        // Given
        when(profileMapper.selectOne(any())).thenReturn(testProfile);

        // When
        String profileId = growthProfileService.generateProfile("stu_001", "2026-03");

        // Then
        assertEquals("profile_001", profileId);
        verify(profileMapper, never()).insert(any());
    }

    /**
     * TC-AI-002: 获取AI成长画像
     */
    @Test
    void testGetProfile_Success() {
        // Given
        when(profileMapper.selectOne(any())).thenReturn(testProfile);

        // When
        ProfileVO vo = growthProfileService.getProfile("stu_001", "2026-03");

        // Then
        assertNotNull(vo);
        assertEquals("profile_001", vo.getProfileId());
        assertEquals(85, vo.getEmotionScore());
        assertEquals(85, vo.getOverallScore());
    }

    /**
     * TC-AI-002: 获取AI成长画像 - 不存在
     */
    @Test
    void testGetProfile_NotFound() {
        // Given
        when(profileMapper.selectOne(any())).thenReturn(null);

        // When
        ProfileVO vo = growthProfileService.getProfile("stu_001", "2026-03");

        // Then
        assertNull(vo);
    }

    /**
     * TC-REPORT-001: 生成月度成长报告
     */
    @Test
    void testGenerateMonthlyReport_NewReport() {
        // Given
        when(reportMapper.selectOne(any())).thenReturn(null);
        when(reportMapper.insert(any(MonthlyReport.class))).thenReturn(1);

        // When
        String reportId = growthProfileService.generateMonthlyReport("stu_001", "2026-03");

        // Then
        assertNotNull(reportId);
        verify(reportMapper, times(1)).insert(any(MonthlyReport.class));
    }

    /**
     * TC-REPORT-002: 获取月度报告
     */
    @Test
    void testGetMonthlyReport_Success() {
        // Given
        when(reportMapper.selectOne(any())).thenReturn(testReport);

        // When
        ReportVO vo = growthProfileService.getMonthlyReport("stu_001", "2026-03");

        // Then
        assertNotNull(vo);
        assertEquals("report_001", vo.getReportId());
        assertEquals(20, vo.getAttendanceDays());
    }

    /**
     * TC-REPORT-003: 审核报告 - 通过
     */
    @Test
    void testAuditReport_Approve() {
        // Given
        when(reportMapper.selectOne(any())).thenReturn(testReport);
        when(reportMapper.updateById(any())).thenReturn(1);

        // When
        growthProfileService.auditReport("report_001", true);

        // Then
        verify(reportMapper, times(1)).updateById(any());
    }

    /**
     * TC-REPORT-003: 审核报告 - 拒绝
     */
    @Test
    void testAuditReport_Reject() {
        // Given
        when(reportMapper.selectOne(any())).thenReturn(testReport);
        when(reportMapper.updateById(any())).thenReturn(1);

        // When
        growthProfileService.auditReport("report_001", false);

        // Then
        verify(reportMapper, times(1)).updateById(any());
    }
}