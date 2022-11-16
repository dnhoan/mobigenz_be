package com.api.mobigenz_be.configs.securities.jwt.security;

import org.springframework.security.core.AuthenticationException;

public class UserNotActivatedException extends AuthenticationException {

    public UserNotActivatedException(String msg) {
        super(msg);
    }

}