package com.kgms.data.service;

import com.kgms.common.util.IdGenerator;
import com.kgms.data.entity.Conversation;
import com.kgms.data.entity.Message;
import com.kgms.data.mapper.ConversationMapper;
import com.kgms.data.mapper.MessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private ConversationMapper conversationMapper;

    /**
     * 发送私信
     */
    public Message sendMessage(String senderId, String senderType, String receiverId, String receiverType,
                                String messageType, String content, String mediaUrl, Integer mediaDuration) {
        // 创建或获取会话ID
        String conversationId = getOrCreateConversation(senderId, receiverId);

        Message message = new Message();
        message.setMessageId(IdGenerator.generateId("MSG"));
        message.setConversationId(conversationId);
        message.setSenderId(senderId);
        message.setSenderType(senderType);
        message.setReceiverId(receiverId);
        message.setReceiverType(receiverType);
        message.setMessageType(messageType != null ? messageType : "TEXT");
        message.setContent(content);
        message.setMediaUrl(mediaUrl);
        message.setMediaDuration(mediaDuration);
        message.setIsRead(0);

        messageMapper.insert(message);

        // 更新会话最后消息
        updateConversationLastMessage(conversationId, content);

        log.info("Message sent from {} to {}", senderId, receiverId);
        return message;
    }

    /**
     * 发送图片消息
     */
    public Message sendImageMessage(String senderId, String senderType, String receiverId, String receiverType, String imageUrl) {
        return sendMessage(senderId, senderType, receiverId, receiverType, "IMAGE", null, imageUrl, null);
    }

    /**
     * 发送语音消息
     */
    public Message sendVoiceMessage(String senderId, String senderType, String receiverId, String receiverType, String voiceUrl, Integer duration) {
        return sendMessage(senderId, senderType, receiverId, receiverType, "VOICE", null, voiceUrl, duration);
    }

    /**
     * 获取会话列表
     */
    public List<Map<String, Object>> getConversationList(String userId) {
        return messageMapper.selectConversationList(userId);
    }

    /**
     * 获取会话消息历史
     */
    public List<Message> getConversationMessages(String conversationId) {
        return messageMapper.selectByConversationId(conversationId);
    }

    /**
     * 标记消息为已读
     */
    public void markAsRead(String conversationId, String userId) {
        messageMapper.markAsRead(conversationId, userId);
        log.info("Messages marked as read in conversation {}", conversationId);
    }

    /**
     * 删除消息
     */
    public void deleteMessage(String messageId) {
        messageMapper.deleteByMessageId(messageId);
        log.info("Message deleted: {}", messageId);
    }

    /**
     * 创建群组
     */
    public Conversation createGroup(String name, String ownerId, List<String> memberIds) {
        Conversation conversation = new Conversation();
        conversation.setConversationId(IdGenerator.generateId("GRP"));
        conversation.setConversationType("GROUP");
        conversation.setName(name);
        conversation.setOwnerId(ownerId);

        // 添加群主到成员列表
        List<String> allMembers = new ArrayList<>(memberIds);
        if (!allMembers.contains(ownerId)) {
            allMembers.add(ownerId);
        }

        conversation.setMemberIds(convertListToJson(allMembers));
        conversation.setMemberCount(allMembers.size());
        conversation.setIsMuted(0);

        conversationMapper.insert(conversation);
        log.info("Group created: {}", conversation.getConversationId());
        return conversation;
    }

    /**
     * 获取或创建私聊会话
     */
    private String getOrCreateConversation(String userId1, String userId2) {
        // 确保ID顺序一致，以便查找现有会话
        String key1 = userId1.compareTo(userId2) < 0 ? userId1 : userId2;
        String key2 = userId1.compareTo(userId2) < 0 ? userId2 : userId1;

        String conversationId = "conv_" + key1 + "_" + key2;

        // 检查会话是否存在
        Conversation existing = conversationMapper.selectByConversationId(conversationId);
        if (existing == null) {
            Conversation conversation = new Conversation();
            conversation.setConversationId(conversationId);
            conversation.setConversationType("PRIVATE");
            conversation.setMemberIds(convertListToJson(Arrays.asList(userId1, userId2)));
            conversation.setMemberCount(2);
            conversation.setIsMuted(0);
            conversationMapper.insert(conversation);
        }

        return conversationId;
    }

    private void updateConversationLastMessage(String conversationId, String content) {
        Conversation conversation = conversationMapper.selectByConversationId(conversationId);
        if (conversation != null) {
            conversation.setLastMessage(content);
            conversation.setLastMessageTime(new Date());
            conversationMapper.updateByConversationId(conversation);
        }
    }

    private String convertListToJson(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "[]";
        }
        return String.join(",", list);
    }
}
