package com.techbro.sammychatbot.models.users.controller;

import com.techbro.sammychatbot.commons.CustomResponse;
import com.techbro.sammychatbot.models.users.dto.SignupRequest;
import com.techbro.sammychatbot.models.users.service.UsersService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("AdminController")
@AllArgsConstructor
@RequestMapping("api/v1/admin/")
public class AdminController {

    private final UsersService usersService;

    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signupRequest){
        return CustomResponse.bake("Registration Successful",201,usersService.adminSignup(signupRequest));
    }


}
