package com.kgms.data.entity;

import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SystemConfig extends BaseEntity {

    private String configId;
    private String configKey;
    private String configValue;
    private String configType;
    private String description;
    private Integer isEnabled;
}
