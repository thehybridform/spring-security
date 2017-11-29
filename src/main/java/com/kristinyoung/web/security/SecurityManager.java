package com.kristinyoung.web.security;

import com.kristinyoung.model.User;

public interface SecurityManager {

    enum Token {
        KRISTINYOUNG
    }

    String createToken(User user);
    User userFor(String token);

    class UserNotDefinedException extends RuntimeException {
        public UserNotDefinedException() {
            super("User not defined");
        }
    }
}
