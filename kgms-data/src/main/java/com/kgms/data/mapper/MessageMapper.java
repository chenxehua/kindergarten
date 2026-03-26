package com.kgms.data.mapper;

import com.kgms.data.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MessageMapper {

    Message selectById(@Param("id") Long id);

    Message selectByMessageId(@Param("messageId") String messageId);

    List<Message> selectByConversationId(@Param("conversationId") String conversationId);

    List<Map<String, Object>> selectConversationList(@Param("userId") String userId);

    int insert(Message message);

    int updateByMessageId(Message message);

    int deleteByMessageId(@Param("messageId") String messageId);

    int markAsRead(@Param("conversationId") String conversationId, @Param("receiverId") String receiverId);
}
