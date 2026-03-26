package com.kgms.notice.dto;

import lombok.Data;

import java.util.Date;

/**
 * 推送记录DTO
 */
@Data
public class PushRecordDTO {

    private String pushId;
    private String userId;
    private String pushType;
    private String title;
    private String content;
    private String relatedId;
    private String channel;
    private String status;
    private Date sendTime;
    private Date readTime;
}
