package com.kgms.notice.dto;

import lombok.Data;

/**
 * 通知DTO
 */
@Data
public class NoticeDTO {
    /** 幼儿园ID */
    private String kgId;
    /** 标题 */
    private String title;
    /** 内容 */
    private String content;
    /** 通知类型 */
    private String noticeType;
    /** 目标类型 */
    private String targetType;
    /** 目标ID列表 */
    private String targetIds;
}
