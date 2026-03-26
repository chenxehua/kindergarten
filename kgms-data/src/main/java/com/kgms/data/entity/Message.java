package com.kgms.data.entity;

import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 私信消息实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Message extends BaseEntity {

    private String messageId;
    private String conversationId;
    private String senderId;
    private String senderType;
    private String receiverId;
    private String receiverType;

    // 消息内容
    private String messageType;
    private String content;
    private String mediaUrl;
    private Integer mediaDuration;

    // 消息状态
    private Integer isRead;
    private Date readTime;
}
