package com.techbro.sammychatbot.chat.service;


import com.techbro.sammychatbot.chat.dto.ChatRequest;
import com.techbro.sammychatbot.chat.dto.ChatResponse;
import com.techbro.sammychatbot.commons.CustomResponse;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{
    @Override
    public ChatResponse prompt(ChatRequest chatRequest) {
        return null;
    }

    @Override
    public CustomResponse getRecentChat() {
        return null;
    }

    @Override
    public ChatResponse getSingleChatMessage(Long chatId) {
        return null;
    }

    @Override
    public CustomResponse getChatHistory(Long stringChatRoomReference) {
        return null;
    }
}
