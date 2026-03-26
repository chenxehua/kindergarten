package com.kgms.data.controller;

import com.kgms.common.result.Result;
import com.kgms.data.entity.Conversation;
import com.kgms.data.entity.Message;
import com.kgms.data.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/messages")
@Slf4j
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 发送私信
     */
    @PostMapping("/send")
    public Result<Message> sendMessage(
            @RequestParam String senderId,
            @RequestParam String senderType,
            @RequestParam String receiverId,
            @RequestParam String receiverType,
            @RequestParam(required = false) String messageType,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String mediaUrl,
            @RequestParam(required = false) Integer mediaDuration) {
        return Result.success(messageService.sendMessage(senderId, senderType, receiverId, receiverType,
                messageType, content, mediaUrl, mediaDuration));
    }

    /**
     * 发送图片消息
     */
    @PostMapping("/send/image")
    public Result<Message> sendImageMessage(
            @RequestParam String senderId,
            @RequestParam String senderType,
            @RequestParam String receiverId,
            @RequestParam String receiverType,
            @RequestParam String imageUrl) {
        return Result.success(messageService.sendImageMessage(senderId, senderType, receiverId, receiverType, imageUrl));
    }

    /**
     * 发送语音消息
     */
    @PostMapping("/send/voice")
    public Result<Message> sendVoiceMessage(
            @RequestParam String senderId,
            @RequestParam String senderType,
            @RequestParam String receiverId,
            @RequestParam String receiverType,
            @RequestParam String voiceUrl,
            @RequestParam Integer duration) {
        return Result.success(messageService.sendVoiceMessage(senderId, senderType, receiverId, receiverType, voiceUrl, duration));
    }

    /**
     * 获取会话列表
     */
    @GetMapping("/conversations")
    public Result<List<Map<String, Object>>> getConversationList(@RequestParam String userId) {
        return Result.success(messageService.getConversationList(userId));
    }

    /**
     * 获取会话消息历史
     */
    @GetMapping("/conversation/{conversationId}")
    public Result<List<Message>> getConversationMessages(@PathVariable String conversationId) {
        return Result.success(messageService.getConversationMessages(conversationId));
    }

    /**
     * 标记消息为已读
     */
    @PostMapping("/conversation/{conversationId}/read")
    public Result<Void> markAsRead(
            @PathVariable String conversationId,
            @RequestParam String userId) {
        messageService.markAsRead(conversationId, userId);
        return Result.success();
    }

    /**
     * 删除消息
     */
    @DeleteMapping("/{messageId}")
    public Result<Void> deleteMessage(@PathVariable String messageId) {
        messageService.deleteMessage(messageId);
        return Result.success();
    }

    /**
     * 创建群组
     */
    @PostMapping("/group")
    public Result<Conversation> createGroup(
            @RequestParam String name,
            @RequestParam String ownerId,
            @RequestParam List<String> memberIds) {
        return Result.success(messageService.createGroup(name, ownerId, memberIds));
    }
}
