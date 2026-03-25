package com.kgms.notice.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_notice")
public class Notice extends BaseEntity {
    private String noticeId;
    private String kgId;
    private String title;
    private String content;
    private String noticeType;
    private String targetType;
    private String targetIds;
    private String attachUrls;
    private String publishBy;
    private LocalDateTime publishTime;
    private LocalDateTime expireTime;
    private Integer status;
}
