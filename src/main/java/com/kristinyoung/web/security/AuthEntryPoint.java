package com.kristinyoung.web.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class AuthEntryPoint implements AuthenticationEntryPoint {

    private static final String ACCESS_DENIED = "{\"reponse\":{\"type\":\"ACCESS_DENIED\",\"message\":\"Access Denied\"}}";

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException {
        response.getWriter().println(getJson());
        response.getWriter().close();
    }

    private String getJson() {
        return ACCESS_DENIED;
    }

}
