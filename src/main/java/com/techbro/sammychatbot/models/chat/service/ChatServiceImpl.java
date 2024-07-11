package com.techbro.sammychatbot.models.chat.service;


import com.techbro.sammychatbot.models.chat.dto.ChatDTO;
import com.techbro.sammychatbot.models.chat.dto.ChatRequest;
import com.techbro.sammychatbot.models.chat.dto.RecentChats;
import com.techbro.sammychatbot.models.chat.entity.ChatEntity;
import com.techbro.sammychatbot.models.chat.entity.ChatRoomEntity;
import com.techbro.sammychatbot.models.chat.entity.ChatUser;
import com.techbro.sammychatbot.models.chat.repository.ChatEntityRepository;
import com.techbro.sammychatbot.models.chat.repository.ChatRoomEntityRepository;
import com.techbro.sammychatbot.commons.CustomResponse;
import com.techbro.sammychatbot.models.users.model.UserEntity;
import com.techbro.sammychatbot.models.users.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService{
    private final ChatRoomEntityRepository roomRepository;
    private final ChatEntityRepository entityRepository;
    private final UsersService userService;

    @Override
    @Transactional
    public ResponseEntity<?> prompt(ChatRequest chatRequest) {
        var user = userService.getAuthenticatedUser();
        String reply = "TEMPORAL DEMO REPLY";//TODO - prompt ai

        ChatRoomEntity chat_room = getChatRoom(chatRequest, user);
        ChatEntity userPrompt = ChatMapper.mapToChatEntity(chat_room, chatRequest.getMessage(), ChatUser.SENDER);
        ChatEntity ai_reply = ChatMapper.mapToChatEntity(chat_room, reply, ChatUser.RECIPIENT);

        entityRepository.saveAll(new ArrayList<>(List.of(userPrompt,ai_reply)));
        return CustomResponse.bake("Prompt Successful",201,ChatMapper.mapCHatToDTO(ai_reply));
    }

    @Override
    public ResponseEntity<?> getRecentChat(Pageable pageable) {
        UserEntity loggedInUser = userService.getAuthenticatedUser();
        List<ChatRoomEntity> chatRooms = roomRepository.findByUserId(loggedInUser.getId());//TODO -> Apply Pagination
        List<RecentChats> recentChats = chatRooms.stream().map(ChatMapper::mapToRecentChat).toList();
        return CustomResponse.bake("Recent Chat Retrieved Successfully",200,recentChats);//TODO -> Replace bake with paginate
    }

    @Override
    public ResponseEntity<?> getSingleChatMessage(Long chatId) {
        ChatDTO chatDTO = entityRepository.findById(chatId)
                .map(ChatMapper::mapCHatToDTO)
                .orElseThrow(() -> new IllegalArgumentException("Chat Message Not Found"));
        return CustomResponse.bake("Chat Message Retrieved Successfully",200,chatDTO);
    }

    @Override
    public ResponseEntity<?> getChatHistory(Long chatRoomId,Pageable pageable) {
        Page<ChatEntity> paginatedChatEntities = entityRepository.findByChatRoomEntityId(chatRoomId,pageable);
        List<ChatDTO> chatDTOList = paginatedChatEntities.stream().map(ChatMapper::mapCHatToDTO).toList();
        return CustomResponse.paginate("Chats Retrieved Successfully",200,chatDTOList,paginatedChatEntities);
    }


    private ChatRoomEntity getChatRoom(ChatRequest chatRequest, UserEntity user) {//find or create chatRoom
        var optional = roomRepository.findById(chatRequest.getChatRoomId());
        return optional.orElseGet(() -> roomRepository.save(ChatRoomEntity.builder()
                .user(user)
                .stringChatReference(chatRequest.getMessage())//TODO - reduce title to some words
                .build()));
    }

}
