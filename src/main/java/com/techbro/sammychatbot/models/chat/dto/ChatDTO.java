package com.techbro.sammychatbot.models.chat.dto;

import com.techbro.sammychatbot.models.chat.entity.ChatUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    private Long id;
    private Long chatRoomId;
    private String message;
    private ChatUser userRole = ChatUser.SENDER;
}
