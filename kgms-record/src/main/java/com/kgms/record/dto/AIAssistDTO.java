package com.kgms.record.dto;

import lombok.Data;

import java.util.List;

/**
 * AI辅助填写DTO
 */
@Data
public class AIAssistDTO {

    /** 学生ID */
    private String studentId;
    /** 记录日期 */
    private String recordDate;
    /** 记录类型 */
    private String recordType;
    /** 已有信息(可选) */
    private String existingInfo;
    /** 需要填充的维度 */
    private List<String> dimensions;
}