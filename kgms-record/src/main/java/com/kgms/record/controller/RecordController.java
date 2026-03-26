package com.kgms.record.controller;

import com.kgms.common.result.PageResult;
import com.kgms.common.result.Result;
import com.kgms.record.dto.*;
import com.kgms.record.service.RecordAIService;
import com.kgms.record.service.RecordService;
import com.kgms.record.service.RecordTemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/record")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;
    private final RecordAIService recordAIService;
    private final RecordTemplateService templateService;

    /**
     * 保存成长记录（草稿）
     */
    @PostMapping("/save")
    public Result<String> saveRecord(
            @RequestBody @Valid RecordDTO dto,
            @RequestHeader("X-User-Id") String teacherId,
            @RequestHeader(value = "X-Class-Id", required = false) String classId) {
        String recordId = recordService.saveRecord(dto, teacherId, classId, false);
        return Result.success(recordId);
    }

    /**
     * 发布成长记录
     */
    @PostMapping("/publish")
    public Result<String> publishRecord(
            @RequestBody @Valid RecordDTO dto,
            @RequestHeader("X-User-Id") String teacherId,
            @RequestHeader(value = "X-Class-Id", required = false) String classId) {
        String recordId = recordService.saveRecord(dto, teacherId, classId, true);
        return Result.success(recordId);
    }

    /**
     * 单独发布记录
     */
    @PostMapping("/publish/existing")
    public Result<Void> publishExistingRecord(@RequestParam String recordId) {
        recordService.publishRecord(recordId);
        return Result.success();
    }

    /**
     * 获取记录详情
     */
    @GetMapping("/detail")
    public Result<RecordVO> getRecordDetail(@RequestParam String recordId) {
        return Result.success(recordService.getRecordDetail(recordId));
    }

    /**
     * 获取学生记录列表
     */
    @GetMapping("/list")
    public Result<PageResult<RecordVO>> getStudentRecords(
            @RequestParam String studentId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        return Result.success(recordService.getStudentRecords(studentId, startDate, endDate, page, pageSize));
    }

    /**
     * 获取某天记录
     */
    @GetMapping("/by-date")
    public Result<RecordVO> getRecordByDate(
            @RequestParam String studentId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return Result.success(recordService.getRecordByDate(studentId, date));
    }

    // ==================== 语音输入 ====================

    /**
     * 语音识别
     */
    @PostMapping("/voice/recognize")
    public Result<String> recognizeVoice(@RequestParam String voiceUrl) {
        return Result.success(recordAIService.recognizeVoice(voiceUrl));
    }

    // ==================== AI辅助 ====================

    /**
     * AI辅助填写
     */
    @PostMapping("/ai/assist")
    public Result<AIAssistResultDTO> aiAssist(@RequestBody AIAssistDTO dto) {
        return Result.success(recordAIService.assistFill(dto));
    }

    /**
     * 检查未填写项
     */
    @GetMapping("/ai/check-unfilled")
    public Result<Map<String, List<String>>> checkUnfilled(
            @RequestParam String studentId,
            @RequestParam String recordDate) {
        return Result.success(recordAIService.checkUnfilled(studentId, recordDate));
    }

    /**
     * 自动填充重复信息
     */
    @GetMapping("/ai/auto-fill")
    public Result<Map<String, String>> autoFillRepeatInfo(
            @RequestParam String studentId,
            @RequestParam String recordType) {
        return Result.success(recordAIService.autoFillRepeatInfo(studentId, recordType));
    }

    // ==================== 快捷模板 ====================

    /**
     * 获取所有模板
     */
    @GetMapping("/templates")
    public Result<List<RecordTemplateDTO>> getAllTemplates() {
        return Result.success(templateService.getAllTemplates());
    }

    /**
     * 获取默认模板
     */
    @GetMapping("/templates/default")
    public Result<List<RecordTemplateDTO>> getDefaultTemplates() {
        return Result.success(templateService.getDefaultTemplates());
    }

    /**
     * 根据场景获取模板
     */
    @GetMapping("/templates/scenario")
    public Result<List<RecordTemplateDTO>> getTemplatesByScenario(@RequestParam String scenario) {
        return Result.success(templateService.getTemplatesByScenario(scenario));
    }

    /**
     * 获取热门模板
     */
    @GetMapping("/templates/popular")
    public Result<List<RecordTemplateDTO>> getPopularTemplates(@RequestParam(defaultValue = "5") int limit) {
        return Result.success(templateService.getPopularTemplates(limit));
    }

    /**
     * 创建模板
     */
    @PostMapping("/templates")
    public Result<String> createTemplate(@RequestBody RecordTemplateDTO dto) {
        return Result.success(templateService.createTemplate(dto));
    }

    /**
     * 更新模板
     */
    @PutMapping("/templates")
    public Result<Void> updateTemplate(@RequestBody RecordTemplateDTO dto) {
        templateService.updateTemplate(dto);
        return Result.success();
    }

    /**
     * 删除模板
     */
    @DeleteMapping("/templates")
    public Result<Void> deleteTemplate(@RequestParam String templateId) {
        templateService.deleteTemplate(templateId);
        return Result.success();
    }

    // ==================== 批量操作 ====================

    /**
     * 批量创建记录
     */
    @PostMapping("/batch")
    public Result<Void> batchCreateRecord(@RequestBody BatchRecordDTO dto) {
        recordService.batchCreateRecord(dto);
        return Result.success();
    }
}
