package com.kristinyoung.web.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;

public class UserDetailService implements AuthenticationProvider {

    @Override
    public Authentication authenticate(final Authentication authentication) {
        return authentication;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return true;
    }
}
