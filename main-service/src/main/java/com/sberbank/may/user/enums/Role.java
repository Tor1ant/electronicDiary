package com.sberbank.may.user.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_PARENT,
    ROLE_TEACHER;
    @Override
    public String getAuthority() {
        return name();
    }
}