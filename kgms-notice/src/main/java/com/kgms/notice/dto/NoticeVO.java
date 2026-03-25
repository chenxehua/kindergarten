package com.kgms.notice.dto;
import lombok.Data;
import java.time.LocalDateTime;
@Data
public class NoticeVO {
    private String noticeId;
    private String kgId;
    private String title;
    private String content;
    private String noticeType;
    private String publishBy;
    private LocalDateTime publishTime;
}
