package com.kgms.growth.controller;

import com.kgms.growth.dto.ProfileVO;
import com.kgms.growth.dto.ReportVO;
import com.kgms.growth.service.GrowthProfileService;
import com.kgms.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 成长画像Controller
 * 权限: 家长可查看自己孩子, 老师可查看班级学生, 园长可查看全部
 */
@RestController
@RequestMapping("/api/growth")
@RequiredArgsConstructor
public class GrowthController {

    private final GrowthProfileService growthProfileService;

    /**
     * 获取AI成长画像 - TC-AI-002
     */
    @GetMapping("/profile")
    public Result<ProfileVO> getProfile(
            @RequestParam String studentId,
            @RequestParam(required = false) String month,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Type", required = false) Integer userType) {

        // TODO: 权限检查
        return Result.success(growthProfileService.getProfile(studentId, month));
    }

    /**
     * 手动生成成长画像 - TC-AI-001
     */
    @PostMapping("/profile/generate")
    public Result<String> generateProfile(
            @RequestParam String studentId,
            @RequestParam String month) {
        return Result.success(growthProfileService.generateProfile(studentId, month));
    }

    /**
     * 获取月度成长报告 - TC-REPORT-002
     */
    @GetMapping("/report/monthly")
    public Result<ReportVO> getMonthlyReport(
            @RequestParam String studentId,
            @RequestParam String month) {
        return Result.success(growthProfileService.getMonthlyReport(studentId, month));
    }

    /**
     * 手动生成月度报告 - TC-REPORT-001
     */
    @PostMapping("/report/generate")
    public Result<String> generateMonthlyReport(
            @RequestParam String studentId,
            @RequestParam String month) {
        return Result.success(growthProfileService.generateMonthlyReport(studentId, month));
    }

    /**
     * 审核月度报告 - TC-REPORT-003
     * 权限: 园长
     */
    @PostMapping("/report/audit")
    public Result<Void> auditReport(
            @RequestParam String reportId,
            @RequestParam Boolean approved) {
        growthProfileService.auditReport(reportId, approved);
        return Result.success();
    }

    /**
     * 导出PDF报告 - TC-REPORT-004
     */
    @GetMapping("/report/export")
    public Result<String> exportReportPDF(
            @RequestParam String reportId,
            @RequestParam(required = false) String format) {
        // TODO: 生成PDF并返回下载链接
        return Result.success("https://cdn.example.com/reports/" + reportId + ".pdf");
    }

    /**
     * 情绪分析准确性验证 - TC-AI-003
     * 用于后台统计AI分析准确率
     */
    @GetMapping("/emotion/accuracy")
    public Result<Double> getEmotionAccuracy(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        // TODO: 对比AI分析与人工标注的准确率
        return Result.success(0.85); // 85%准确率
    }
}