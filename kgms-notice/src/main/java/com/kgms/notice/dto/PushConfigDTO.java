package com.kgms.notice.dto;

import lombok.Data;

import java.util.List;

/**
 * 推送配置DTO
 */
@Data
public class PushConfigDTO {

    private String configId;
    private String kgId;
    private String pushType;
    private Boolean enabled;
    private String pushTime;
    private String templateId;
    private String quietHoursStart;
    private String quietHoursEnd;
    private List<String> quietDays;
}