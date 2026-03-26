package com.kgms.record.dto;

import lombok.Data;

import java.util.List;

/**
 * 批量填写DTO
 */
@Data
public class BatchRecordDTO {

    private String classId;
    private String recordDate;
    /** 批量选择的学生ID */
    private List<String> studentIds;
    /** 记录内容 */
    private String content;
    /** 记录类型 */
    private String recordType;
    /** 照片ID列表 */
    private List<String> photoIds;
}
