package com.techbro.sammychatbot.chat.entity;

import jakarta.persistence.*;

@Entity
public class ChatEntity {
    @Id
    @SequenceGenerator(
            name = "cha_seq",
            sequenceName = "cha_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "cha_seq",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    @ManyToOne
    private ChatRoomEntity chatRoomEntity;
    private String message;
}
