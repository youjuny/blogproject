package com.korea.test.user;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIM("ROLE_ADMIN"),
    USER("USER_ROLE");

    UserRole(String value) {
        this.value = value;
    }

    private String value;



}
