package com.kristinyoung.web.security;

import com.google.common.collect.Lists;
import com.kristinyoung.model.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class AuthenticationInterceptorFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
        authenticate(req);
        chain.doFilter(req, res);
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException { }

    @Override
    public void destroy() { }

    private SecurityManager getSecurityManager(final ServletRequest req) {
        return getContext(req).getBean(SecurityManager.class);
    }

    private WebApplicationContext getContext(final ServletRequest req) {
        return WebApplicationContextUtils.getWebApplicationContext(req.getServletContext());
    }

    private String getToken(final HttpServletRequest req) {
        for (final Cookie c: Lists.newArrayList(req.getCookies())) {
            if (SecurityManager.Token.KRISTINYOUNG.name().equals(c.getName())) {
                return c.getValue();
            }
        }

        return null;
    }

    private void authenticate(final ServletRequest req) {
        SecurityContextHolder.getContext().setAuthentication(AuthenticationFactory.createAuth(getUser(req)));
    }

    private User getUser(final ServletRequest req) {
        return getSecurityManager(req).userFor(getToken((HttpServletRequest) req));
    }

}
