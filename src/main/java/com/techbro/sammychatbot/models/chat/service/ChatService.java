package com.techbro.sammychatbot.models.chat.service;

import com.techbro.sammychatbot.models.chat.dto.ChatRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ChatService {

    ResponseEntity<?> prompt(ChatRequest chatRequest);
    ResponseEntity<?> getRecentChat(Pageable pageable);
    ResponseEntity<?> getSingleChatMessage(Long chatId);
    ResponseEntity<?> getChatHistory(Long chatRoomId,Pageable pageable);
}
