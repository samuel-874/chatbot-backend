package com.techbro.sammychatbot.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techbro.sammychatbot.commons.CustomResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private static final Logger log = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseEntity<?> responseEntity = CustomResponse.bake(accessDeniedException.getMessage(), 400);
        log.info("backend_log: access denied, log:"+accessDeniedException.getMessage());
        Map<String,String> payload = new LinkedHashMap<>();
        payload.put("error","true");
        payload.put("message","Access Denied");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String json = new ObjectMapper().writeValueAsString(payload);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
