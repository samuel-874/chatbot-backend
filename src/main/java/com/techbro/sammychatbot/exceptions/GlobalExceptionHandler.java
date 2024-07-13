package com.techbro.sammychatbot.exceptions;

import com.techbro.sammychatbot.commons.CustomResponse;
import com.techbro.sammychatbot.exceptions.custom_exceptions.AIPromptException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalState(IllegalStateException illegalStateException){
        return CustomResponse.bake(illegalStateException.getMessage(),400);
    }

     @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalState(IllegalArgumentException illegalArgumentException){
        return CustomResponse.bake(illegalArgumentException.getMessage(),400);
    }

     @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleIllegalState(UsernameNotFoundException exception){
        return CustomResponse.bake(exception.getMessage(),400);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> allHandler(Exception exception){
        return CustomResponse.bake(exception.getMessage(),500);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialException(BadCredentialsException credentialsException){
        return CustomResponse.bake("Error Processing Request",500);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> hadInvalidArgument(MethodArgumentNotValidException notValidException){
        List<String> errors = notValidException.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
        return CustomResponse.bake("Invalid Inputs",400,errors);
    }

    @ExceptionHandler(AIPromptException.class)
    public ResponseEntity<?> handleAIPrompt(AIPromptException exception){
        System.out.println("Error Occurred While Prompting AI"+exception.getMessage());
        return CustomResponse.bake("Something went wrong! Please try again",500);
    }

}
