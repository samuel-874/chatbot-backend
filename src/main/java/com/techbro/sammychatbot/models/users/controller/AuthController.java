package com.techbro.sammychatbot.models.users.controller;

import com.techbro.sammychatbot.commons.CustomResponse;
import com.techbro.sammychatbot.models.users.dto.SignupRequest;
import com.techbro.sammychatbot.models.users.service.UsersService;
import com.techbro.sammychatbot.models.users.dto.AuthenticationRequest;
import com.techbro.sammychatbot.models.users.dto.PasswordRestRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController("AuthController")
@RequestMapping("api/v1/auth/user/")
public class AuthController {
    private final UsersService usersService;


    @PostMapping("register")
    public ResponseEntity<?> register(@Valid  @RequestBody SignupRequest signupRequest){
        return CustomResponse.bake("Registration Successful",201,usersService.signup(signupRequest));
    }

    @GetMapping("checking")
    public ResponseEntity<?> register(){
        return CustomResponse.bake("Registration Successful",201,null);
    }

    @PostMapping("authenticate")
    public ResponseEntity<?> userLogin(@Valid  @RequestBody AuthenticationRequest authenticationRequest){
        return CustomResponse.bake("Login Successful",200,usersService.login(authenticationRequest));
    }
    @PostMapping("password_reset_otp/{email}")
    public ResponseEntity<?> requestPasswordReset(@PathVariable("email") String email){
        return usersService.sendPasswordResetToken(email);
    }
    @PostMapping("validate_password_reset_otp")
    public ResponseEntity<?> validatePasswordReset(@RequestParam String email, @RequestParam String otp){
        return usersService.validateToken(email, otp);
    }

    @PostMapping("reset_password")
    public ResponseEntity<?> passwordReset(@Valid @RequestBody PasswordRestRequest passwordRestRequest){
        return usersService.resetPassword(passwordRestRequest);
    }




    @PostMapping("authenticate/admin")
    public ResponseEntity<?> adminLogin(@Valid  @RequestBody AuthenticationRequest authenticationRequest){
        return CustomResponse.bake("Login Successful",200,usersService.adminLogin(authenticationRequest));
    }





}
