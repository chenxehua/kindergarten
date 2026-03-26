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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * AI成长画像和月度报告服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GrowthProfileService {

    private final GrowthProfileMapper profileMapper;
    private final MonthlyReportMapper reportMapper;

    // ==================== AI成长画像 ====================

    /**
     * 获取成长画像 - TC-AI-002
     */
    public ProfileVO getProfile(String studentId, String month) {
        GrowthProfile profile = profileMapper.selectOne(
                new LambdaQueryWrapper<GrowthProfile>()
                        .eq(GrowthProfile::getStudentId, studentId)
                        .eq(GrowthProfile::getProfileMonth, month)
        );

        if (profile == null) return null;

        return convertProfileToVO(profile);
    }

    /**
     * 生成成长画像 - TC-AI-001
     * 1. 获取学生本月所有成长记录
     * 2. 调用AI服务进行分析
     * 3. 生成各维度评分和建议
     */
    public String generateProfile(String studentId, String month) {
        log.info("生成成长画像: studentId={}, month={}", studentId, month);

        // 检查是否已存在
        GrowthProfile existing = profileMapper.selectOne(
                new LambdaQueryWrapper<GrowthProfile>()
                        .eq(GrowthProfile::getStudentId, studentId)
                        .eq(GrowthProfile::getProfileMonth, month)
        );

        if (existing != null) {
            return existing.getProfileId();
        }

        // TODO: 调用AI服务分析
        // 获取本月成长记录 -> AI分析 -> 生成画像

        // 模拟AI分析结果
        GrowthProfile profile = new GrowthProfile();
        profile.setProfileId(java.util.UUID.randomUUID().toString());
        profile.setStudentId(studentId);
        profile.setProfileMonth(month);

        // 各维度评分
        profile.setEmotionScore(85);
        profile.setEmotionDetail("{\"highEmotion\":\"开心\",\"stability\":0.9}");

        profile.setSocialScore(80);
        profile.setSocialDetail("{\"interaction\":0.85,\"sharing\":0.75}");

        profile.setLearningScore(88);
        profile.setLearningDetail("{\"participation\":0.9,\"focus\":0.85}");

        profile.setSportScore(82);
        profile.setSportDetail("{\"activity\":0.88,\"coordination\":0.78}");

        profile.setDietScore(90);
        profile.setDietDetail("{\"appetite\":0.95,\"nutrition\":0.88}");

        profile.setOverallScore(85);
        profile.setAiAnalysis("本月表现优秀，情绪稳定，与同伴相处融洽，参与活动积极。建议：继续加强精细动作练习。");
        profile.setSuggestions("[\"多进行户外运动\",\"加强语言表达能力\"]");
        profile.setWarnings("[]");

        profileMapper.insert(profile);

        log.info("成长画像生成完成: profileId={}", profile.getProfileId());
        return profile.getProfileId();
    }

    // ==================== 月度报告 ====================

    /**
     * 获取月度报告 - TC-REPORT-002
     */
    public ReportVO getMonthlyReport(String studentId, String month) {
        MonthlyReport report = reportMapper.selectOne(
                new LambdaQueryWrapper<MonthlyReport>()
                        .eq(MonthlyReport::getStudentId, studentId)
                        .eq(MonthlyReport::getReportMonth, month)
        );

        if (report == null) return null;

        return convertReportToVO(report);
    }

    /**
     * 生成月度报告 - TC-REPORT-001
     */
    public String generateMonthlyReport(String studentId, String month) {
        log.info("生成月度报告: studentId={}, month={}", studentId, month);

        // 检查是否已存在
        MonthlyReport existing = reportMapper.selectOne(
                new LambdaQueryWrapper<MonthlyReport>()
                        .eq(MonthlyReport::getStudentId, studentId)
                        .eq(MonthlyReport::getReportMonth, month)
        );

        if (existing != null) {
            return existing.getReportId();
        }

        // TODO: 调用AI服务生成报告
        // 汇总出勤、成长记录、照片 -> AI生成分析 -> 生成报告

        MonthlyReport report = new MonthlyReport();
        report.setReportId(java.util.UUID.randomUUID().toString());
        report.setStudentId(studentId);
        report.setReportMonth(month);

        // 统计数据
        report.setAttendanceDays(20);
        report.setTotalDays(22);

        // 成长亮点
        report.setHighlights("[\"能够独立完成手工制作\",\"与同伴分享玩具\",\"主动向老师问好\"]");

        // 各维度数据
        report.setDimensionData("{\"emotion\":85,\"social\":80,\"learning\":88,\"sport\":82,\"diet\":90}");

        // AI总结
        report.setAiSummary("本月成长显著，动手能力增强，语言表达能力有进步。");
        // 教师寄语
        report.setTeacherSummary("孩子本月表现很好，继续保持！");

        // 精选照片
        report.setFeaturedPhotos("[\"photo1.jpg\",\"photo2.jpg\",\"photo3.jpg\"]");

        // 状态：待审核
        report.setStatus(0);

        reportMapper.insert(report);

        log.info("月度报告生成完成: reportId={}", report.getReportId());
        return report.getReportId();
    }

    /**
     * 审核报告 - TC-REPORT-003
     */
    public void auditReport(String reportId, boolean approved) {
        MonthlyReport report = reportMapper.selectOne(
                new LambdaQueryWrapper<MonthlyReport>().eq(MonthlyReport::getReportId, reportId)
        );

        if (report == null) {
            throw new BusinessException(404, "报告不存在");
        }

        report.setStatus(approved ? 2 : 3); // 2=已发布 3=已拒绝
        report.setPublishTime(approved ? LocalDateTime.now() : null);
        reportMapper.updateById(report);

        // TODO: 审核通过后通知家长
        log.info("报告审核完成: reportId={}, approved={}", reportId, approved);
    }

    // ==================== 转换方法 ====================

    private ProfileVO convertProfileToVO(GrowthProfile profile) {
        ProfileVO vo = new ProfileVO();
        vo.setProfileId(profile.getProfileId());
        vo.setStudentId(profile.getStudentId());
        vo.setProfileMonth(profile.getProfileMonth());

        // 情绪维度
        vo.setEmotionScore(profile.getEmotionScore());
        vo.setEmotionLevel(getLevelText(profile.getEmotionScore()));
        vo.setEmotionTrend("UP");
        vo.setEmotionDetail(profile.getEmotionDetail());

        // 社交维度
        vo.setSocialScore(profile.getSocialScore());
        vo.setSocialLevel(getLevelText(profile.getSocialScore()));
        vo.setSocialTrend("FLAT");
        vo.setSocialDetail(profile.getSocialDetail());

        // 学习维度
        vo.setLearningScore(profile.getLearningScore());
        vo.setLearningLevel(getLevelText(profile.getLearningScore()));
        vo.setLearningTrend("UP");
        vo.setLearningDetail(profile.getLearningDetail());

        // 运动维度
        vo.setSportScore(profile.getSportScore());
        vo.setSportLevel(getLevelText(profile.getSportScore()));
        vo.setSportTrend("FLAT");
        vo.setSportDetail(profile.getSportDetail());

        // 饮食维度
        vo.setDietScore(profile.getDietScore());
        vo.setDietLevel(getLevelText(profile.getDietScore()));
        vo.setDietTrend("UP");
        vo.setDietDetail(profile.getDietDetail());

        vo.setOverallScore(profile.getOverallScore());
        vo.setAiAnalysis(profile.getAiAnalysis());

        // 解析建议和预警
        try {
            vo.setSuggestions(parseJsonToList(profile.getSuggestions()));
            vo.setWarnings(parseJsonToWarnings(profile.getWarnings()));
        } catch (Exception e) {
            log.error("Error parsing profile data", e);
        }

        vo.setGeneratedAt(profile.getCreateTime() != null ? profile.getCreateTime().toString() : null);
        return vo;
    }

    private ReportVO convertReportToVO(MonthlyReport report) {
        ReportVO vo = new ReportVO();
        vo.setReportId(report.getReportId());
        vo.setStudentId(report.getStudentId());
        vo.setReportMonth(report.getReportMonth());

        // 出勤统计
        vo.setAttendanceDays(report.getAttendanceDays());
        vo.setTotalDays(report.getTotalDays());
        if (report.getTotalDays() != null && report.getAttendanceDays() != null && report.getTotalDays() > 0) {
            vo.setAttendanceRate(Math.round((double) report.getAttendanceDays() / report.getTotalDays() * 100 * 100.0) / 100.0);
        }
        vo.setAttendanceTrend("持平");

        // 解析成长亮点
        try {
            vo.setHighlights(parseJsonToList(report.getHighlights()));
            vo.setDimensionData(parseJsonToMap(report.getDimensionData()));
            vo.setFeaturedPhotos(parseJsonToList(report.getFeaturedPhotos()));
        } catch (Exception e) {
            log.error("Error parsing report data", e);
        }

        vo.setAiSummary(report.getAiSummary());
        vo.setTeacherSummary(report.getTeacherSummary());
        vo.setStatus(report.getStatus());

        // 状态文本
        if (report.getStatus() != null) {
            switch (report.getStatus()) {
                case 0: vo.setStatusText("生成中"); break;
                case 1: vo.setStatusText("待审核"); break;
                case 2: vo.setStatusText("已发布"); break;
                case 3: vo.setStatusText("已拒绝"); break;
                default: vo.setStatusText("未知");
            }
        }

        vo.setPublishTime(report.getPublishTime() != null ? report.getPublishTime().toString() : null);
        vo.setCreateTime(report.getCreateTime() != null ? report.getCreateTime().toString() : null);

        // 生成H5和PDF链接
        vo.setH5Url("/api/growth/report/h5/" + report.getReportId());
        vo.setPdfUrl("/api/growth/report/export?reportId=" + report.getReportId());

        return vo;
    }

    /**
     * 获取历史月份画像对比
     */
    public List<ProfileVO> getProfileHistory(String studentId, int months) {
        List<GrowthProfile> profiles = profileMapper.selectList(
                new LambdaQueryWrapper<GrowthProfile>()
                        .eq(GrowthProfile::getStudentId, studentId)
                        .orderByDesc(GrowthProfile::getProfileMonth)
                        .last("LIMIT " + months)
        );

        return profiles.stream()
                .map(this::convertProfileToVO)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 获取班级学生画像汇总
     */
    public Map<String, Object> getClassProfileSummary(String classId) {
        Map<String, Object> summary = new java.util.HashMap<>();

        // TODO: 从数据库查询班级学生画像统计数据
        summary.put("avgEmotionScore", 85.0);
        summary.put("avgSocialScore", 82.0);
        summary.put("avgLearningScore", 88.0);
        summary.put("avgSportScore", 80.0);
        summary.put("avgDietScore", 86.0);
        summary.put("totalStudents", 25);

        return summary;
    }

    private String getLevelText(Integer score) {
        if (score == null) return "未知";
        if (score >= 90) return "优秀";
        if (score >= 80) return "良好";
        if (score >= 70) return "中等";
        if (score >= 60) return "及格";
        return "需关注";
    }

    private List<String> parseJsonToList(String json) {
        if (json == null || json.isEmpty()) return java.util.Collections.emptyList();
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().readValue(json,
                    new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {});
        } catch (Exception e) {
            return java.util.Collections.singletonList(json);
        }
    }

    private List<Map<String, Object>> parseJsonToWarnings(String json) {
        if (json == null || json.isEmpty()) return java.util.Collections.emptyList();
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().readValue(json,
                    new com.fasterxml.jackson.core.type.TypeReference<List<Map<String, Object>>>() {});
        } catch (Exception e) {
            return java.util.Collections.emptyList();
        }
    }

    private Map<String, Object> parseJsonToMap(String json) {
        if (json == null || json.isEmpty()) return java.util.Collections.emptyMap();
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().readValue(json, Map.class);
        } catch (Exception e) {
            return java.util.Collections.emptyMap();
        }
    }
}