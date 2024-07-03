package com.techbro.sammychatbot.users.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {
    @Id
    @SequenceGenerator(
            name = "user_entity_seq",
            sequenceName = "user_entity_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "user_entity_seq",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    @Column(unique = true,nullable = false)
    private String email;
    private String password;
    private String fullName;
    @CreationTimestamp
    private LocalDateTime registeredOn;
    @UpdateTimestamp
    private LocalDateTime lastUpdated;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_role")
    private Roles roles = Roles.USER;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(nullable = true)
    private UserEntity registeredBy;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.getAuthorities();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
