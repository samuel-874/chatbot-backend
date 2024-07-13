package com.techbro.sammychatbot.models.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(length = 3000)
    private String message;
    @Builder.Default
    private ChatUser userRole = ChatUser.SENDER;
}
