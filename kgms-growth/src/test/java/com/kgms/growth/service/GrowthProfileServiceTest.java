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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        testProfile = new GrowthProfile();
        testProfile.setId(1L);
        testProfile.setProfileId("profile_001");
        testProfile.setStudentId("stu_001");
        testProfile.setProfileMonth("2026-03");
        testProfile.setEmotionScore(85);
        testProfile.setSocialScore(80);
        testProfile.setLearningScore(85);
        testProfile.setSportScore(90);
        testProfile.setDietScore(85);
        testProfile.setOverallScore(85);
        
        testReport = new MonthlyReport();
        testReport.setId(1L);
        testReport.setReportId("report_001");
        testReport.setStudentId("stu_001");
        testReport.setReportMonth("2026-03");
    }

    @Test
    void testGetProfile() {
        when(profileMapper.selectOne(any())).thenReturn(testProfile);
        ProfileVO vo = growthProfileService.getProfile("stu_001", "2026-03");
        assertNotNull(vo);
    }

    @Test
    void testGetMonthlyReport() {
        when(reportMapper.selectOne(any())).thenReturn(testReport);
        ReportVO vo = growthProfileService.getMonthlyReport("stu_001", "2026-03");
        assertNotNull(vo);
    }

    @Test
    void testGenerateProfile() {
        when(profileMapper.selectOne(any())).thenReturn(null);
        when(profileMapper.insert(any(GrowthProfile.class))).thenReturn(1);
        String result = growthProfileService.generateProfile("stu_001", "2026-03");
        assertNotNull(result);
    }

    @Test
    void testGenerateMonthlyReport() {
        when(reportMapper.selectOne(any())).thenReturn(null);
        when(reportMapper.insert(any(MonthlyReport.class))).thenReturn(1);
        String result = growthProfileService.generateMonthlyReport("stu_001", "2026-03");
        assertNotNull(result);
    }

    @Test
    void testGetReportHistory() {
        // TODO: 需要在服务中实现
    }
}
