package com.techbro.sammychatbot.users.service;

import com.techbro.sammychatbot.commons.CustomResponse;
import com.techbro.sammychatbot.config.jwt.JwtService;
import com.techbro.sammychatbot.users.dto.*;
import com.techbro.sammychatbot.users.model.OTPToken;
import com.techbro.sammychatbot.users.model.Roles;
import com.techbro.sammychatbot.users.model.UserEntity;
import com.techbro.sammychatbot.users.repository.UserRepository;
import com.techbro.sammychatbot.users.service.token.OTPTokensService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UsersService{
    private final UserRepository userRepository;
    private final OTPTokensService otpTokensService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final HttpServletResponse httpServletResponse;

    @Override
    public AuthResponse signup(SignupRequest signupRequest) {
        boolean isExisting = userRepository.existsByEmail(signupRequest.getEmail());
        if(isExisting) throw new IllegalStateException("Email Taken");
        UserEntity newUser = UserMapper.mapRequestToEntity(signupRequest);
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        newUser.setPassword(encodedPassword);
        newUser.setRoles(Roles.USER);
        userRepository.save(newUser);

        return generateToken(newUser);
    }

    @Override
    public AuthResponse adminSignup(SignupRequest signupRequest) {
        boolean isExisting = userRepository.existsByEmail(signupRequest.getEmail());
        if(isExisting) throw new IllegalStateException("Email Taken");
        UserEntity registerer = getAuthenticatedAdmin();

        UserEntity newUser = UserMapper.mapRequestToEntity(signupRequest);
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        newUser.setPassword(encodedPassword);
        newUser.setRegisteredBy(registerer);
        newUser.setRoles(Roles.ADMIN);
        userRepository.save(newUser);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                newUser.getRoles(),
                newUser.getPassword()));

        return generateToken(newUser);
    }

    @Override
    public void defaultAdmin(SignupRequest signupRequest) {
        boolean isExisting = userRepository.existsByEmail(signupRequest.getEmail());
        if(isExisting)return;//pulse execution
        UserEntity newUser = UserMapper.mapRequestToEntity(signupRequest);
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        newUser.setPassword(encodedPassword);
        newUser.setRoles(Roles.ADMIN);
        userRepository.save(newUser);
    }

    @Override
    public AuthResponse login(AuthenticationRequest authenticationRequest) {
        UserEntity user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        boolean match = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if(!match) throw new IllegalArgumentException("Incorrect Password");
       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword()));
        return generateToken(user);
    }
    @Override
    public AuthResponse adminLogin(AuthenticationRequest authenticationRequest) {
        UserEntity user = userRepository.findByEmailAndRoles(authenticationRequest.getEmail(),Roles.ADMIN).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()));
        return generateToken(user);
    }

    private AuthResponse generateToken(UserEntity user){
        var token = jwtService.generateToken(user.getEmail());
       return AuthResponse.builder()
               .fullName(user.getFullName())
               .email(user.getEmail())
               .token(token).build();
    }

    @Override
    public UserDTO getProfile(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(username == null) throw new IllegalStateException("UnAuthorized");
        var user = userRepository.findByEmail(username).orElseThrow(() -> new IllegalStateException("Unauthorized User"));
        return UserMapper.mapUserToDTO(user);
    }

    @Override
    public UserEntity getAuthenticatedUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(username == null) throw new IllegalStateException("UnAuthorized");
        return userRepository.findByEmail(username).orElseThrow(() -> new IllegalStateException("Unauthorized User"));
    }

    @Override
    public ResponseEntity<?> sendPasswordResetToken(String email) {
        boolean isExistingByEmail = userRepository.existsByEmail(email);
        if(!isExistingByEmail) return CustomResponse.bake("User not existing",404);
        OTPToken token = otpTokensService.generateOTP(email);
        System.out.println(token);
        // todo send to user email
        return CustomResponse.bake("Six digit OTP sent successfully",201);
    }

    @Override
    public ResponseEntity<?> validateToken(String email, String token) {
        boolean isValid = otpTokensService.validateOTP(email,token);
        if(isValid) {
            return CustomResponse.bake("OTP validated successfully", 200);
        };
        return CustomResponse.bake("OTP expired",400);
    }

    @Override
    public ResponseEntity<?> resetPassword(PasswordRestRequest passwordRestRequest) {
        UserEntity user = userRepository.findByEmail(passwordRestRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not existing"));
        boolean oldPasswordMatchNew =  passwordEncoder.matches(passwordRestRequest.getNewPassword(), user.getPassword());
        if(oldPasswordMatchNew)return CustomResponse.bake("New Password Cannot be the same as the old ",400);
        boolean hasValidatedOTP = otpTokensService.hasBeenValidated(passwordRestRequest.getEmail(), passwordRestRequest.getOtp());
        if(!hasValidatedOTP) return CustomResponse.bake("You'll need to validate an otp",400);
        String password = passwordEncoder.encode(passwordRestRequest.getNewPassword());
        user.setPassword(password);
        userRepository.save(user);
        return CustomResponse.bake("Password Update Was Successful",200);
    }

    public UserEntity getAuthenticatedAdmin(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmailAndRoles(email,Roles.ADMIN).orElseThrow(() -> new IllegalStateException("Unauthorized User"));
    }

}
