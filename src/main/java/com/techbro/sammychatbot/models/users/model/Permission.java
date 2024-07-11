package com.techbro.sammychatbot.models.users.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    USER_READ("user:read"),
    USER_POST("user:post"),

    ADMIN_READ("admin:read"),
    ADMIN_POST("admin:post")

    ;
    @Getter
    private final String permission;
}
