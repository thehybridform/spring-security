package com.kristinyoung.model;

import com.google.common.collect.Lists;
import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Role {
        ADMIN,
        MEMBER,
    }

    @BusinessKey
    private final String id;
    private final String username;
    private final String password;
    private final List<Role> roles = Lists.newArrayList();

    private User(final String id, final String username, final String password, final Role... roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles.addAll(Arrays.asList(roles));
    }

    public static User create(final String id, final String username, final String password, final Role... roles) {
        return new User(id, username, password, roles);
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

    public List<Role> getRoles() {
        return Collections.unmodifiableList(Lists.newArrayList(roles));
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
