package com.kgms.video.entity;

import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 视频模板实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VideoTemplate extends BaseEntity {

    private String templateId;
    private String templateName;
    private String templateType;

    // 模板信息
    private String previewUrl;
    private String templateUrl;
    private Integer duration;
    private String defaultMusic;

    // 样式配置
    private String styleConfig;
    private String textStyle;
    private String transitionEffect;

    // 状态
    private Integer isDefault;
    private Integer status;
    private String createBy;
}
