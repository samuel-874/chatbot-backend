package com.techbro.sammychatbot.models.chat.repository;

import com.techbro.sammychatbot.models.chat.entity.ChatRoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomEntityRepository extends JpaRepository<ChatRoomEntity,Long> {
    List<ChatRoomEntity> findByUserId(Long userId);
    Page<ChatRoomEntity> findByUserId(Long userId, Pageable pageable);
}
