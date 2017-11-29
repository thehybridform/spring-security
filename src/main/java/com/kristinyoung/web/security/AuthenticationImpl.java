package com.kristinyoung.web.security;

import com.google.common.collect.Lists;
import com.kristinyoung.model.User;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

final class AuthenticationImpl implements Authentication {

    private static final long serialVersionUID = 1L;

    private final String principal;
    private final Collection<GrantedAuthorityImpl> authorities = Lists.newArrayList();

    private AuthenticationImpl(final User user) {
        this.principal = user.getId();
    }

    static AuthenticationImpl create(final User user) {
        return new AuthenticationImpl(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableCollection(authorities);
    }

    void addAuthority(final GrantedAuthorityImpl authority) {
        authorities.add(authority);
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
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(final boolean authenticated) {
        throw new IllegalArgumentException("Cannot change the authentication state once set.");
    }

    @Override
    public String getName() {
        return AuthenticationImpl.class.getName();
    }

}
