package com.kgms.data.service;

import com.kgms.common.util.IdGenerator;
import com.kgms.data.entity.SystemConfig;
import com.kgms.data.mapper.SystemConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SystemConfigService {

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    /**
     * 获取所有配置
     */
    public List<SystemConfig> getAllConfigs() {
        return systemConfigMapper.selectAll();
    }

    /**
     * 根据Key获取配置值
     */
    public String getConfigValue(String configKey) {
        SystemConfig config = systemConfigMapper.selectByConfigKey(configKey);
        return config != null ? config.getConfigValue() : null;
    }

    /**
     * 获取配置值（带默认值）
     */
    public String getConfigValue(String configKey, String defaultValue) {
        String value = getConfigValue(configKey);
        return value != null ? value : defaultValue;
    }

    /**
     * 获取Boolean类型配置
     */
    public Boolean getBooleanConfig(String configKey) {
        String value = getConfigValue(configKey);
        if (value == null) {
            return false;
        }
        return "true".equalsIgnoreCase(value) || "1".equals(value);
    }

    /**
     * 获取Integer类型配置
     */
    public Integer getIntegerConfig(String configKey) {
        String value = getConfigValue(configKey);
        if (value == null) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 更新配置
     */
    public SystemConfig updateConfig(String configKey, String configValue) {
        SystemConfig config = systemConfigMapper.selectByConfigKey(configKey);
        if (config == null) {
            throw new RuntimeException("配置不存在: " + configKey);
        }

        config.setConfigValue(configValue);
        systemConfigMapper.updateByConfigId(config);
        log.info("Config updated: {} = {}", configKey, configValue);
        return config;
    }

    /**
     * 更新功能开关
     */
    public SystemConfig updateFeatureToggle(String configKey, boolean enabled) {
        return updateConfig(configKey, enabled ? "true" : "false");
    }

    /**
     * 创建配置
     */
    public SystemConfig createConfig(String configKey, String configValue, String configType, String description) {
        SystemConfig existing = systemConfigMapper.selectByConfigKey(configKey);
        if (existing != null) {
            throw new RuntimeException("配置已存在: " + configKey);
        }

        SystemConfig config = new SystemConfig();
        config.setConfigId(IdGenerator.generateId("CFG"));
        config.setConfigKey(configKey);
        config.setConfigValue(configValue);
        config.setConfigType(configType);
        config.setDescription(description);
        config.setIsEnabled(1);

        systemConfigMapper.insert(config);
        log.info("Config created: {}", configKey);
        return config;
    }

    /**
     * 获取所有功能开关
     */
    public Map<String, Boolean> getFeatureToggles() {
        List<SystemConfig> configs = systemConfigMapper.selectAll();
        Map<String, Boolean> toggles = new HashMap<>();

        for (SystemConfig config : configs) {
            if (config.getConfigKey().startsWith("feature.")) {
                toggles.put(config.getConfigKey(), getBooleanConfig(config.getConfigKey()));
            }
        }

        return toggles;
    }

    /**
     * 获取AI画像功能是否启用
     */
    public boolean isAiProfileEnabled() {
        return getBooleanConfig("feature.ai.profile.enabled");
    }

    /**
     * 获取AI视频功能是否启用
     */
    public boolean isAiVideoEnabled() {
        return getBooleanConfig("feature.ai.video.enabled");
    }

    /**
     * 获取考勤功能是否启用
     */
    public boolean isAttendanceEnabled() {
        return getBooleanConfig("feature.attendance.enabled");
    }

    /**
     * 获取家校沟通功能是否启用
     */
    public boolean isMessageEnabled() {
        return getBooleanConfig("feature.message.enabled");
    }
}
