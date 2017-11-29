package com.kristinyoung.service;

import com.google.common.collect.Maps;
import com.kristinyoung.UserFacade;
import com.kristinyoung.model.User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
final class UserFacadeImpl implements UserFacade {

    private final Map<String, User> users = Maps.newHashMap();

    UserFacadeImpl() {
        addUser(User.create("super-id", "super", "password", User.Role.ADMIN, User.Role.MEMBER));
        addUser(User.create("admin-id", "admin", "password", User.Role.ADMIN));
        addUser(User.create("member-id", "member", "password", User.Role.MEMBER));
    }

    @Override
    public User findUser(final String userId) {
        return users.get(userId);
    }

    @Override
    public User findUser(final String username, final String password) {
        for (Map.Entry<String, User> entry: users.entrySet()) {
            if (entry.getValue().getUsername().equals(username) && entry.getValue().getPassword().equals(password)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private void addUser(final User user) {
        users.put(user.getId(), user);
    }
}
