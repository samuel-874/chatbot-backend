package com.techbro.sammychatbot.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
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
public class SignupRequest {
    @NotBlank(message = "full_name is required")
    @JsonProperty("full_name")
    private String fullName;

    @NotBlank(message = "password is required")
    @Length(min = 6,message = "password must be atleast six character")
    private String password;

    @Email(message = "invalid email provided")
    @NotBlank(message = "email is required")
    private String email;
}
