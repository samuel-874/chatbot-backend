package com.techbro.sammychatbot.config;

import com.techbro.sammychatbot.models.users.dto.SignupRequest;
import com.techbro.sammychatbot.models.users.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AppInitializer implements ApplicationRunner {
    private final UsersService usersService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
            usersService.defaultAdmin(new SignupRequest("Abiodun Samuel","password@**","samuelab846@gmail.com"));
    }
}
