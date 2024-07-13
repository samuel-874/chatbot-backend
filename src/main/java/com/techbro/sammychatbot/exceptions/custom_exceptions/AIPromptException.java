package com.techbro.sammychatbot.exceptions.custom_exceptions;


public class AIPromptException extends RuntimeException{
    public AIPromptException(String message){
        super(message);
    }
}
