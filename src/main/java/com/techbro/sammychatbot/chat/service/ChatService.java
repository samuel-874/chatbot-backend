package com.techbro.sammychatbot.chat.service;

import com.techbro.sammychatbot.chat.dto.ChatRequest;
import com.techbro.sammychatbot.chat.dto.ChatResponse;
import com.techbro.sammychatbot.commons.CustomResponse;

public interface ChatService {

    ChatResponse prompt(ChatRequest chatRequest);
    CustomResponse getRecentChat();
    ChatResponse getSingleChatMessage(Long chatId);
    CustomResponse getChatHistory(Long stringChatRoomReference);
}
