package com.kristinyoung.web.security;

import java.util.Collection;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public final class AuthenticationVoter implements AccessDecisionVoter<Object>  {

    @Override
    public boolean supports(final ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(final Class clazz) {
        return true;
    }

    @Override
    public int vote(final Authentication auth, final Object object, final Collection<ConfigAttribute> attributes) {
        if (!auth.isAuthenticated()) {
            return AccessDecisionVoter.ACCESS_DENIED;
        }

        return isAuthorized(auth, attributes) ? AccessDecisionVoter.ACCESS_GRANTED : AccessDecisionVoter.ACCESS_DENIED;
    }

    private boolean isAuthorized(final Authentication auth, final Collection<ConfigAttribute> attributes) {
        for (final GrantedAuthority ga: auth.getAuthorities()) {
            for (final ConfigAttribute ca: attributes) {
                if (ga.getAuthority().equalsIgnoreCase(ca.getAttribute())) {
                    return true;
                }
            }
        }

        return false;
    }
}
