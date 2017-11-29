package com.kristinyoung.web.rest;

import com.google.common.collect.Lists;
import com.kristinyoung.UserFacade;
import com.kristinyoung.web.model.User;
import com.kristinyoung.web.security.SecurityManager;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RestController {

    private static final int ONE_YEAR = 60 * 60 * 24 * 365;

    private final SecurityManager securityManager;
    private final UserFacade userFacade;

    @Autowired
    RestController(final SecurityManager securityManager, final UserFacade userFacade) {
        this.securityManager = securityManager;
        this.userFacade = userFacade;
    }

    @RequestMapping(
        value = "login",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void login(final HttpServletRequest req, final HttpServletResponse res) throws Exception {
        final com.kristinyoung.model.User user = userFacade.findUser(req.getParameter("username"), req.getParameter("password"));

        if (user != null) {
            addTokenCookie(res, user);
        }

        res.sendRedirect("/");
    }

    @RequestMapping(
        value = "logout",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void logout(final HttpServletRequest req, final HttpServletResponse res) throws Exception {
        deleteCookie(req);
        res.sendRedirect("/");
    }

    @RequestMapping(
        value = "/member-or-admin",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured({ "MEMBER", "ADMIN" })
    public User getAsMemberOrAdmin(final HttpServletRequest req) {
        return getUser(req);
    }

    @RequestMapping(
        value = "/member-only",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured({ "MEMBER" })
    public User getAsMember(final HttpServletRequest req) {
        return getUser(req);
    }

    @RequestMapping(
        value = "/admin-only",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured({ "ADMIN" })
    public User getAsAdmin(final HttpServletRequest req) {
        return getUser(req);
    }

    private User getUser(final HttpServletRequest req) {
        return User.create(securityManager.userFor(getToken(req)));
    }

    private String getToken(final HttpServletRequest req) {
        for (final Cookie c: Lists.newArrayList(req.getCookies())) {
            if (SecurityManager.Token.KRISTINYOUNG.name().equals(c.getName())) {
                return c.getValue();
            }
        }
        return null;
    }

    private void addTokenCookie(final HttpServletResponse res, final com.kristinyoung.model.User user) {
        res.addCookie(createCookie(user));
    }

    private Cookie createCookie(final com.kristinyoung.model.User user) {
        final Cookie c = new Cookie(SecurityManager.Token.KRISTINYOUNG.name(), securityManager.createToken(user));
        c.setMaxAge(ONE_YEAR);
        c.setPath("/");
        return c;
    }

    private void deleteCookie(final HttpServletRequest req) {
        Lists.newArrayList(req.getCookies()).forEach(c -> c.setMaxAge(0));
    }
}
