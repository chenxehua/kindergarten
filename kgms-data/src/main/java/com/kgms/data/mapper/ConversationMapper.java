package com.kgms.data.mapper;

import com.kgms.data.entity.Conversation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ConversationMapper {

    Conversation selectById(@Param("id") Long id);

    Conversation selectByConversationId(@Param("conversationId") String conversationId);

    List<Conversation> selectByOwnerId(@Param("ownerId") String ownerId);

    int insert(Conversation conversation);

    int updateByConversationId(Conversation conversation);

    int deleteByConversationId(@Param("conversationId") String conversationId);
}
