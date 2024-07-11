package com.techbro.sammychatbot.models.chat.repository;

import com.techbro.sammychatbot.models.chat.entity.ChatEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatEntityRepository extends JpaRepository<ChatEntity,Long> {
    Page<ChatEntity> findByChatRoomEntityId(Long chatRoomId, Pageable pageable);
}
