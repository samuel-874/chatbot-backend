package com.techbro.sammychatbot.models.chat.controller;

import com.techbro.sammychatbot.models.chat.dto.ChatRequest;
import com.techbro.sammychatbot.models.chat.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("ChatController")
@RequestMapping("/api/v1/chat/")
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("prompt")
    public ResponseEntity<?> sendPrompt(ChatRequest chatRequest){
        return chatService.prompt(chatRequest);
    }

    @GetMapping("recent")
    public ResponseEntity<?> getRecentChats(Pageable pageable){
        return chatService.getRecentChat(pageable);
    }

    @GetMapping("chats/{chatId}")
    public ResponseEntity<?> getSingleChatMessage(@PathVariable("chatId")Long chatId){
        return chatService.getSingleChatMessage(chatId);
    }

    @GetMapping("{chatRoomId}/chat_messages")
    public ResponseEntity<?> getChatHistory(@PathVariable("chatRoomId") Long chatRoomId,Pageable pageable){
        return chatService.getChatHistory(chatRoomId,pageable);
    }

}
