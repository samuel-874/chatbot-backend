package com.techbro.sammychatbot.users.dto;

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

public class AuthenticationRequest {
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "password is required")
    @Length(min = 6,message = "password must not be less than six")
    private String password;
}
