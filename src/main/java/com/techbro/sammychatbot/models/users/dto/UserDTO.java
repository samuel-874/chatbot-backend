package com.techbro.sammychatbot.models.users.dto;

import com.techbro.sammychatbot.models.users.model.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String fullName;
    private String email;
    private Roles roles;
}
