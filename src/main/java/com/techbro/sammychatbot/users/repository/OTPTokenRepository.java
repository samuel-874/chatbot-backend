package com.techbro.sammychatbot.users.repository;

import com.techbro.sammychatbot.users.model.OTPToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OTPTokenRepository extends JpaRepository<OTPToken, Long> {
    Optional<OTPToken> findByTokenAndEmail(String  token,String email);
}
