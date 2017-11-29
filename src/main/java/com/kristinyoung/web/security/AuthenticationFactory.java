package com.kristinyoung.web.security;

import com.kristinyoung.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

final class AuthenticationFactory {

    private static final Authentication NO_AUTH = new NoAuthentication();

    private AuthenticationFactory() { }

    static Authentication createAuth(final User user) {
        if (user == null) {
            return noAuth();
        }

        final AuthenticationImpl auth = AuthenticationImpl.create(user);
        auth.authenticate(true);

        for (final User.Role r: user.getRoles()) {
            auth.addAuthority(GrantedAuthorityImpl.create(r.name()));
        }

        return auth;
    }

    static Authentication noAuth() {
        return NO_AUTH;
    }

    private static class NoAuthentication implements Authentication {
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
        public void setAuthenticated(final boolean isAuthenticated) {
            // not used
        }

        @Override
        public String getName() {
            return null;
        }
    }
}
