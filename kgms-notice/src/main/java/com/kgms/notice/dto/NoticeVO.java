package com.kgms.notice.dto;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 通知VO
 */
@Data
public class NoticeVO {
    /** 通知ID */
    private String noticeId;
    /** 幼儿园ID */
    private String kgId;
    /** 标题 */
    private String title;
    /** 内容 */
    private String content;
    /** 通知类型 */
    private String noticeType;
    /** 发布人 */
    private String publishBy;
    /** 发布时间 */
    private LocalDateTime publishTime;
}
