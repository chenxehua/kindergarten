package com.kgms.data.controller;

import com.kgms.common.result.Result;
import com.kgms.data.entity.SystemConfig;
import com.kgms.data.service.SystemConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/settings")
@Slf4j
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * 获取所有配置
     */
    @GetMapping
    public Result<List<SystemConfig>> getAllConfigs() {
        return Result.success(systemConfigService.getAllConfigs());
    }

    /**
     * 获取配置值
     */
    @GetMapping("/value")
    public Result<String> getConfigValue(@RequestParam String configKey) {
        return Result.success(systemConfigService.getConfigValue(configKey));
    }

    /**
     * 更新配置
     */
    @PutMapping
    public Result<SystemConfig> updateConfig(
            @RequestParam String configKey,
            @RequestParam String configValue) {
        return Result.success(systemConfigService.updateConfig(configKey, configValue));
    }

    /**
     * 创建配置
     */
    @PostMapping
    public Result<SystemConfig> createConfig(
            @RequestParam String configKey,
            @RequestParam String configValue,
            @RequestParam String configType,
            @RequestParam(required = false) String description) {
        return Result.success(systemConfigService.createConfig(configKey, configValue, configType, description));
    }

    /**
     * 获取所有功能开关
     */
    @GetMapping("/feature-toggles")
    public Result<Map<String, Boolean>> getFeatureToggles() {
        return Result.success(systemConfigService.getFeatureToggles());
    }

    /**
     * 更新功能开关
     */
    @PutMapping("/feature-toggle")
    public Result<SystemConfig> updateFeatureToggle(
            @RequestParam String configKey,
            @RequestParam boolean enabled) {
        return Result.success(systemConfigService.updateFeatureToggle(configKey, enabled));
    }

    /**
     * 获取AI画像功能是否启用
     */
    @GetMapping("/feature/ai-profile")
    public Result<Boolean> isAiProfileEnabled() {
        return Result.success(systemConfigService.isAiProfileEnabled());
    }

    /**
     * 获取AI视频功能是否启用
     */
    @GetMapping("/feature/ai-video")
    public Result<Boolean> isAiVideoEnabled() {
        return Result.success(systemConfigService.isAiVideoEnabled());
    }

    /**
     * 获取考勤功能是否启用
     */
    @GetMapping("/feature/attendance")
    public Result<Boolean> isAttendanceEnabled() {
        return Result.success(systemConfigService.isAttendanceEnabled());
    }

    /**
     * 获取家校沟通功能是否启用
     */
    @GetMapping("/feature/message")
    public Result<Boolean> isMessageEnabled() {
        return Result.success(systemConfigService.isMessageEnabled());
    }
}
