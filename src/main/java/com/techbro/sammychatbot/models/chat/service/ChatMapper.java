package com.techbro.sammychatbot.models.chat.service;

import com.techbro.sammychatbot.models.chat.dto.ChatDTO;
import com.techbro.sammychatbot.models.chat.dto.RecentChats;
import com.techbro.sammychatbot.models.chat.entity.ChatEntity;
import com.techbro.sammychatbot.models.chat.entity.ChatRoomEntity;
import com.techbro.sammychatbot.models.chat.entity.ChatUser;

public class ChatMapper {

    public static ChatEntity mapToChatEntity(ChatRoomEntity chat_room, String chatRequest, ChatUser sender) {
        return ChatEntity.builder()
                .chatRoomEntity(chat_room)
                .message(chatRequest)
                .userRole(sender)
                .build();
    }

    public static ChatDTO mapCHatToDTO(ChatEntity chatEntity){
        return ChatDTO.builder()
                .id(chatEntity.getId())
                .chatRoomId(chatEntity.getChatRoomEntity().getId())
                .message(chatEntity.getMessage())
                .userRole(chatEntity.getUserRole())
                .build();

    }

    public static RecentChats mapToRecentChat(ChatRoomEntity chatRoomEntity){
        return RecentChats.builder()
                .id(chatRoomEntity.getId())
                .title(chatRoomEntity.getStringChatReference())
                .userId(chatRoomEntity.getUser() != null ? chatRoomEntity.getUser().getId() : null)
                .build();
    }


}
