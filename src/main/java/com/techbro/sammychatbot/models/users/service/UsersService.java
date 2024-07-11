package com.techbro.sammychatbot.models.users.service;

import com.techbro.sammychatbot.models.users.dto.*;
import com.techbro.sammychatbot.models.users.model.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UsersService {
    AuthResponse signup(SignupRequest signupRequest);
    AuthResponse adminSignup(SignupRequest signupRequest);
    void defaultAdmin(SignupRequest signupRequest);
    AuthResponse login(AuthenticationRequest authenticationRequest);
    AuthResponse adminLogin(AuthenticationRequest authenticationRequest);

    UserDTO getProfile();

    UserEntity getAuthenticatedUser();
    ResponseEntity<?> sendPasswordResetToken(String email);
    ResponseEntity<?> validateToken(String email,String token);

    ResponseEntity<?> resetPassword(PasswordRestRequest passwordRestRequest);
}
