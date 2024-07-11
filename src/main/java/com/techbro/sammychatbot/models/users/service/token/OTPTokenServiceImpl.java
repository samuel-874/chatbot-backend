package com.techbro.sammychatbot.models.users.service.token;

import com.techbro.sammychatbot.models.users.repository.OTPTokenRepository;
import com.techbro.sammychatbot.models.users.repository.UserRepository;
import com.techbro.sammychatbot.models.users.model.OTPToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@AllArgsConstructor
public class OTPTokenServiceImpl implements OTPTokensService {

    private final OTPTokenRepository otpTokenRepository;
    private final UserRepository userRepository;

    @Override
    public OTPToken generateOTP(String email) {
        OTPToken otpToken = new OTPToken();
        String otp = getOTP();
        var existingToken = otpTokenRepository.findByTokenAndEmail(email,otp);
        existingToken.ifPresent(otpTokenRepository::delete);
        otpToken.setToken(otp);
        otpToken.setEmail(email);
        otpToken.setValidated(false);
        otpToken.setUsed(false);
        LocalDateTime current = LocalDateTime.now();
        otpToken.setCreatedAt(current);
        otpToken.setExpiration(current.plusHours(1));
        otpTokenRepository.save(otpToken);
        return otpToken;
    }

    @Override
    public boolean validateOTP(String email, String otp) {
        OTPToken token = otpTokenRepository.findByTokenAndEmail(otp,email).orElseThrow(() -> new IllegalStateException("Invalid OTP"));
        if(token.getValidated()) throw new IllegalStateException("OTP has been validated already");
        boolean isExpired = token.getExpiration().isBefore(LocalDateTime.now());
        if(isExpired) return false;
        token.setValidated(true);
        otpTokenRepository.save(token);
        return true;
    }

    @Override
    public void updateToken(OTPToken token){
    }

    @Override
    public boolean hasBeenValidated(String email,String token){
        OTPToken otpToken = otpTokenRepository.findByTokenAndEmail(token,email).orElseThrow(() -> new IllegalStateException("OTP has not been validated"));
        if(otpToken.getValidated()){
            otpToken.setUsed(true);
            otpTokenRepository.save(otpToken);
            return true;
        }
        return false;
    }

    private String getOTP(){
        Random random = new Random();
        int num = 100_000 + random.nextInt(900_000);
        return String.valueOf(num);
    }
}
