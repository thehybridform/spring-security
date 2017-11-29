package com.kristinyoung.web.security;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;
import org.springframework.security.core.GrantedAuthority;

final class GrantedAuthorityImpl implements GrantedAuthority {

    @BusinessKey
    private final String authority;

    private GrantedAuthorityImpl(final String authority) {
        this.authority = authority;
    }

    static GrantedAuthorityImpl create(final String authority) {
        return new GrantedAuthorityImpl(authority);
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public boolean equals(final Object obj) {
        return BusinessIdentity.areEqual(this, obj);
    }

    @Override
    public int hashCode() {
        return BusinessIdentity.getHashCode(this);
    }

    @Override
    public String toString() {
        return BusinessIdentity.toString(this);
    }

}
