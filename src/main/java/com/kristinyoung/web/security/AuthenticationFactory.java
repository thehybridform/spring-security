package com.kristinyoung.web.security;

import com.kristinyoung.model.User;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

final class AuthenticationFactory {

    private AuthenticationFactory() { }

    static Authentication createAuth(final User user) {
        if (user == null) {
            return createNoAuthentication();
        }

        final AuthenticationImpl auth = AuthenticationImpl.create(user);
        user.getRoles().forEach(r -> auth.addAuthority(GrantedAuthorityImpl.create(r.name())));

        return auth;
    }

    private static Authentication createNoAuthentication() {
        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.emptyList();
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(final boolean isAuthenticated) { }

            @Override
            public String getName() {
                return null;
            }
        };
    }

}
