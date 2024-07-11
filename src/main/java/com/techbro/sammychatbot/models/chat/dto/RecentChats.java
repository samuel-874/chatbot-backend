package com.techbro.sammychatbot.models.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecentChats {
    private Long id;
    private String title;
    private Long userId;
}
