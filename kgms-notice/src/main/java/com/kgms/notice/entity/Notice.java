package com.kgms.notice.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 通知实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_notice")
public class Notice extends BaseEntity {
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
    /** 目标类型 */
    private String targetType;
    /** 目标ID列表 */
    private String targetIds;
    /** 附件URL */
    private String attachUrls;
    /** 发布人 */
    private String publishBy;
    /** 发布时间 */
    private LocalDateTime publishTime;
    /** 过期时间 */
    private LocalDateTime expireTime;
    /** 状态 */
    private Integer status;
}
