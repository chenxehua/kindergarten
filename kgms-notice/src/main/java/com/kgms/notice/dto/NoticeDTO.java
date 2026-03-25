package com.kgms.notice.dto;
import lombok.Data;
@Data
public class NoticeDTO {
    private String kgId;
    private String title;
    private String content;
    private String noticeType;
    private String targetType;
    private String targetIds;
}
