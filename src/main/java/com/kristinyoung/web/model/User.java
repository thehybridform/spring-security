package com.kristinyoung.web.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.collect.Lists;
import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;

import java.util.List;

@JsonAutoDetect
public final class User {

    @BusinessKey
    private final String id;
    private final String username;
    private final String password;
    private final boolean authenticated;
    private final List<String> roles = Lists.newArrayList();

    public User(final String id, final String username, final String password, final List<com.kristinyoung.model.User.Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authenticated = true;
        roles.forEach(r -> this.roles.add(r.name()));
    }

    public static User create(final com.kristinyoung.model.User user) {
        if (user != null) {
            return new User(user.getId(), user.getUsername(), user.getPassword(), user.getRoles());
        }

        return createUnknown();
    }

    public static User createUnknown() {
        return new User(null, null, null, Lists.newArrayList());
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public List<String> getRoles() {
        return roles;
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
