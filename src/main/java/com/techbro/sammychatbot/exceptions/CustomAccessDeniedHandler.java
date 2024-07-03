package com.techbro.sammychatbot.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techbro.sammychatbot.commons.CustomResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseEntity<?> responseEntity = CustomResponse.bake(accessDeniedException.getMessage(), 400);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String json = new ObjectMapper().writeValueAsString(responseEntity);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
