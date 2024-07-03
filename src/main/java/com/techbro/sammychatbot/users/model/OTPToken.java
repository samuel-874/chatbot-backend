package com.techbro.sammychatbot.users.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "opt_token")
public class OTPToken {
    @Id
    @SequenceGenerator(
            name = "otp_seq",
            sequenceName = "otp_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "otp_seq"
    )
    private Long id;
    private String token;
    private String email;
    private Boolean validated;
    private Boolean used;
    private LocalDateTime expiration;
    private LocalDateTime createdAt;

}
