package com.techbro.sammychatbot.chat.entity;

import com.techbro.sammychatbot.users.model.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
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
    private String stringChatReference;
    @ManyToOne(cascade = CascadeType.DETACH)
    private UserEntity user;
}
