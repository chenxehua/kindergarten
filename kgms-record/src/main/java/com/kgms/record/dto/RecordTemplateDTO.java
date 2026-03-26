package com.kgms.record.dto;

import lombok.Data;

/**
 * 快捷模板DTO
 */
@Data
public class RecordTemplateDTO {

    private String templateId;
    private String templateName;
    /** 模板内容 */
    private String content;
    /** 适用场景 */
    private String scenario;
    /** 是否默认模板 */
    private Boolean isDefault;
    /** 创建人 */
    private String createBy;
}
