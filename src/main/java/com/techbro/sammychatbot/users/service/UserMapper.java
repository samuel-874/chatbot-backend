package com.techbro.sammychatbot.users.service;

import com.techbro.sammychatbot.users.dto.SignupRequest;
import com.techbro.sammychatbot.users.dto.UserDTO;
import com.techbro.sammychatbot.users.model.UserEntity;

public class UserMapper {

    public static UserEntity mapRequestToEntity(SignupRequest signupRequest){
       return UserEntity.builder().fullName(signupRequest.getFullName()).email(signupRequest.getEmail()).build();
    }

    public static UserDTO mapUserToDTO(UserEntity user) {
        if(user == null) return null;
        return  new UserDTO(user.getId(), user.getFullName(), user.getEmail(),user.getRoles());
    }
}
