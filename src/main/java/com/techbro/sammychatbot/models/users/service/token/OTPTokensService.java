package com.techbro.sammychatbot.models.users.service.token;

import com.techbro.sammychatbot.models.users.model.OTPToken;
import org.springframework.stereotype.Service;

@Service
public interface OTPTokensService {
    OTPToken generateOTP(String email);
    boolean validateOTP(String email, String otp);

    void updateToken(OTPToken token);

    boolean hasBeenValidated(String email, String token);
}
