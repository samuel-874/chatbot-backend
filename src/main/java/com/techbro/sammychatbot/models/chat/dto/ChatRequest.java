package com.techbro.sammychatbot.models.chat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {
    private Long chatRoomId;
    @NotBlank(message = "message is required")
    @Length(max = 3000,message = "message cannot be more than 3k characters")
    private String message;
}
