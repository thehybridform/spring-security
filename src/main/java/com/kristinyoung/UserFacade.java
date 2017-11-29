package com.kristinyoung;

import com.kristinyoung.model.User;

public interface UserFacade {
    User findUser(String userId);
    User findUser(String email, String password);
}
