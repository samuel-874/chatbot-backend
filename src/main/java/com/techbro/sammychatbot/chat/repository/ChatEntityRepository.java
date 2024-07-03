package com.techbro.sammychatbot.chat.repository;

import com.techbro.sammychatbot.chat.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatEntityRepository extends JpaRepository<ChatEntity,Long> {
}
