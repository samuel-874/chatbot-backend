package com.techbro.sammychatbot.models.users.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

import static com.techbro.sammychatbot.models.users.model.Permission.*;

@RequiredArgsConstructor
public enum Roles {
    USER(
            Set.of(
                USER_READ,
                USER_POST
            )
    ),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_POST
            )
    )
    ;
    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority> authorities = new java.util.ArrayList<>(getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }
}
