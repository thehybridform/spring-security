package com.kristinyoung.web.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;

@JsonAutoDetect
public final class LoginUser {

    @BusinessKey private final String username;
    @BusinessKey private final String password;
    private boolean success = true;

    private LoginUser(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    @JsonCreator
    public static LoginUser create(@JsonProperty("username") final String email,
                                   @JsonProperty("password") final String password) {
        return new LoginUser(email, password);
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
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
