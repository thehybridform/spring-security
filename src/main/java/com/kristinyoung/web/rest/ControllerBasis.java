package com.kristinyoung.web.rest;

import com.google.common.collect.Lists;
import com.kristinyoung.web.model.User;
import com.kristinyoung.web.security.SecurityManager;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

abstract class ControllerBasis {

    private static final int ONE_YEAR = 60 * 60 * 24 * 365;

    private final SecurityManager securityManager;

    ControllerBasis(final SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    User getUser(final HttpServletRequest req) {
        return User.create(securityManager.userFor(getToken(req)));
    }

    void addTokenCookie(final HttpServletResponse res, final com.kristinyoung.model.User user) {
        res.addCookie(createCookie(securityManager.createToken(user), ONE_YEAR));
    }

    void deleteCookie(final HttpServletResponse res) {
        res.addCookie(createCookie(null, 0));
    }

    private Cookie createCookie(final String token, final int oneYear) {
        final Cookie c = new Cookie(SecurityManager.Token.KRISTINYOUNG.name(), token);
        c.setMaxAge(oneYear);
        c.setPath("/");
        return c;
    }

    private String getToken(final HttpServletRequest req) {
        for (final Cookie c: Lists.newArrayList(req.getCookies())) {
            if (SecurityManager.Token.KRISTINYOUNG.name().equals(c.getName())) {
                return c.getValue();
            }
        }
        return null;
    }

}
