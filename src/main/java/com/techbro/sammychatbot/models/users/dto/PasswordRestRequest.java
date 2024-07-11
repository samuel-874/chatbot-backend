package com.techbro.sammychatbot.models.users.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRestRequest {
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "otp is required")
    private String otp;
    @JsonProperty("new_password")
    @NotBlank(message = "new_password is required")
    @Size(min = 6,message = "new_password must not be less than six")
    private String newPassword;
}
