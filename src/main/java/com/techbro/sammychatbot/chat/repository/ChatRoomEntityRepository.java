package com.techbro.sammychatbot.chat.repository;

import com.techbro.sammychatbot.chat.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomEntityRepository extends JpaRepository<ChatRoomEntity,Long> {}
