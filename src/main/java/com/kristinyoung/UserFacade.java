package com.kristinyoung;

import com.kristinyoung.model.User;

public interface UserFacade {
    User findUser(String userId);
    User findUser(String username, String password);
}
