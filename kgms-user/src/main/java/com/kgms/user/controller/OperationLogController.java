package com.kgms.user.controller;

import com.kgms.common.result.Result;
import com.kgms.user.entity.OperationLog;
import com.kgms.user.service.OperationLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 操作日志控制器
 */
@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class OperationLogController {

    private final OperationLogService operationLogService;

    /**
     * 获取用户操作日志
     */
    @GetMapping("/user/{userId}")
    public Result<List<OperationLog>> getUserLogs(@PathVariable String userId) {
        return Result.success(operationLogService.getUserLogs(userId));
    }

    /**
     * 记录操作日志
     */
    @PostMapping
    public Result<Void> saveLog(@RequestBody OperationLog log) {
        operationLogService.saveLog(log);
        return Result.success(null);
    }
}