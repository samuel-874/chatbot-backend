package com.techbro.sammychatbot.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techbro.sammychatbot.commons.CustomResponse;
import com.techbro.sammychatbot.models.users.repository.UserRepository;
import com.techbro.sammychatbot.models.users.service.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        System.out.printf("request from origin: %s,\npage: %s, \ndevice: %s , \nto_endpoint: %s",request.getServerName(),request.getHeader("origin"),request.getHeader("User-Agent"),request.getRequestURI());

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        jwt = authHeader.substring(7);

        try{
            username = jwtService.extractUsername(jwt);
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if(jwtService.isTokenValid(jwt ,userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }else{
                    sendError(response,CustomResponse.bake("Invalid Token",401));
                }
            }else{
                sendError(response, CustomResponse.bake("Unauthorized: Request Failed",401));
            }


        }catch (ExpiredJwtException e){
            sendError(response,CustomResponse.bake("Jwt Expired",401));
        }

        filterChain.doFilter(request,response);
    }

    void sendError(HttpServletResponse response,Object customResponse) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String json = new ObjectMapper().writeValueAsString(customResponse);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
