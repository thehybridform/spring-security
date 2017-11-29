package com.kristinyoung.web.rest;

import com.kristinyoung.UserFacade;
import com.kristinyoung.web.model.User;
import com.kristinyoung.web.security.SecurityManager;
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
public class RestController extends ControllerBasis {

    private final UserFacade userFacade;

    @Autowired
    RestController(final SecurityManager securityManager, final UserFacade userFacade) {
        super(securityManager);
        this.userFacade = userFacade;
    }

    @RequestMapping(
        value = "login",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void login(final HttpServletRequest req, final HttpServletResponse res) throws Exception {
        final com.kristinyoung.model.User user = userFacade.findUser(req.getParameter("username"), req.getParameter("password"));

        if (user != null) {
            addCookie(res, user);
        }

        res.sendRedirect("/");
    }

    @RequestMapping(
        value = "logout",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void logout(final HttpServletResponse res) throws Exception {
        deleteCookie(res);
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

}
