package com.kgms.record.controller;

import com.kgms.common.result.PageResult;
import com.kgms.common.result.Result;
import com.kgms.record.dto.RecordDTO;
import com.kgms.record.dto.RecordVO;
import com.kgms.record.service.RecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/record")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

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
}
