package com.kgms.growth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kgms.common.exception.BusinessException;
import com.kgms.growth.dto.ProfileVO;
import com.kgms.growth.dto.ReportVO;
import com.kgms.growth.entity.GrowthProfile;
import com.kgms.growth.entity.MonthlyReport;
import com.kgms.growth.mapper.GrowthProfileMapper;
import com.kgms.growth.mapper.MonthlyReportMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GrowthProfileService {
    private final GrowthProfileMapper profileMapper;
    private final MonthlyReportMapper reportMapper;

    public ProfileVO getProfile(String studentId, String month) {
        GrowthProfile profile = profileMapper.selectOne(
                new LambdaQueryWrapper<GrowthProfile>()
                        .eq(GrowthProfile::getStudentId, studentId)
                        .eq(GrowthProfile::getProfileMonth, month)
        );
        if (profile == null) return null;
        ProfileVO vo = new ProfileVO();
        vo.setProfileId(profile.getProfileId());
        vo.setStudentId(profile.getStudentId());
        vo.setProfileMonth(profile.getProfileMonth());
        vo.setEmotionScore(profile.getEmotionScore());
        vo.setSocialScore(profile.getSocialScore());
        vo.setLearningScore(profile.getLearningScore());
        vo.setSportScore(profile.getSportScore());
        vo.setDietScore(profile.getDietScore());
        vo.setOverallScore(profile.getOverallScore());
        vo.setAiAnalysis(profile.getAiAnalysis());
        return vo;
    }

    public String generateProfile(String studentId, String month) {
        // TODO: 调用AI服务生成成长画像
        log.info("生成成长画像: studentId={}, month={}", studentId, month);
        GrowthProfile profile = new GrowthProfile();
        profile.setProfileId(java.util.UUID.randomUUID().toString());
        profile.setStudentId(studentId);
        profile.setProfileMonth(month);
        profile.setEmotionScore(85);
        profile.setSocialScore(80);
        profile.setLearningScore(88);
        profile.setSportScore(82);
        profile.setDietScore(90);
        profile.setOverallScore(85);
        profile.setAiAnalysis("本月表现优秀，情绪稳定，与同伴相处融洽。");
        profileMapper.insert(profile);
        return profile.getProfileId();
    }

    public ReportVO getMonthlyReport(String studentId, String month) {
        MonthlyReport report = reportMapper.selectOne(
                new LambdaQueryWrapper<MonthlyReport>()
                        .eq(MonthlyReport::getStudentId, studentId)
                        .eq(MonthlyReport::getReportMonth, month)
        );
        if (report == null) return null;
        ReportVO vo = new ReportVO();
        vo.setReportId(report.getReportId());
        vo.setStudentId(report.getStudentId());
        vo.setReportMonth(report.getReportMonth());
        vo.setAttendanceDays(report.getAttendanceDays());
        vo.setTotalDays(report.getTotalDays());
        vo.setAiSummary(report.getAiSummary());
        vo.setTeacherSummary(report.getTeacherSummary());
        return vo;
    }

    public String generateMonthlyReport(String studentId, String month) {
        // TODO: 调用AI服务生成月度报告
        log.info("生成月度报告: studentId={}, month={}", studentId, month);
        MonthlyReport report = new MonthlyReport();
        report.setReportId(java.util.UUID.randomUUID().toString());
        report.setStudentId(studentId);
        report.setReportMonth(month);
        report.setAttendanceDays(20);
        report.setTotalDays(22);
        report.setAiSummary("本月成长显著，动手能力增强。");
        report.setTeacherSummary("孩子表现很好，继续保持！");
        report.setStatus(0);
        reportMapper.insert(report);
        return report.getReportId();
    }
}
