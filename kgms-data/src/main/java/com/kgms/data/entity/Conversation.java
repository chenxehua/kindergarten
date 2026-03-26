package com.kgms.data.entity;

import com.kgms.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 会话实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Conversation extends BaseEntity {

    private String conversationId;
    private String conversationType;
    private String name;
    private String avatar;

    // 成员信息
    private String memberIds;
    private Integer memberCount;

    // 群信息
    private String ownerId;
    private String notice;

    // 状态
    private String lastMessage;
    private Date lastMessageTime;
    private Integer isMuted;
}
