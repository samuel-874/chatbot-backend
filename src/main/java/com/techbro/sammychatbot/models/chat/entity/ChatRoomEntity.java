package com.techbro.sammychatbot.models.chat.entity;

import com.techbro.sammychatbot.models.users.model.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomEntity {
    @Id
    @SequenceGenerator(
            name = "chat_room_seq",
            sequenceName = "chat_room_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "chat_room_seq",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    private String stringChatReference;//title
    @ManyToOne(cascade = CascadeType.DETACH)
    private UserEntity user;
}
