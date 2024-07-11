package com.techbro.sammychatbot.models.users.controller;

import com.techbro.sammychatbot.commons.CustomResponse;
import com.techbro.sammychatbot.models.users.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController("UserController")
@RequestMapping("api/v1/user/")
public class UserController {
    private final UsersService usersService;

    @GetMapping("profile")
    public ResponseEntity<?> getAuthenticatedUser(){
        return CustomResponse.bake("Profile Retrieved",200,usersService.getProfile());
    }
}
