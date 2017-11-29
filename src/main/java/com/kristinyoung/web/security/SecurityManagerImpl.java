package com.kristinyoung.web.security;

import com.kristinyoung.TokenFactory;
import com.kristinyoung.UserFacade;
import com.kristinyoung.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
final class SecurityManagerImpl implements SecurityManager {

    private final UserFacade userFacade;
    private final TokenFactory tokenFactory;

    @Autowired
    SecurityManagerImpl(final UserFacade userFacade, final TokenFactory tokenFactory) {
        this.userFacade = userFacade;
        this.tokenFactory = tokenFactory;
    }

    @Override
    public String createToken(final User user) {
        if (user == null) {
            throw new UserNotDefinedException();
        }

        return tokenFactory.createToken(user.getId());
    }

    @Override
    public User userFor(final String token) {
        if (StringUtils.isNoneBlank(token)) {
            return userFacade.findUser(tokenFactory.getMessage(token));
        }

        return null;
    }

}
