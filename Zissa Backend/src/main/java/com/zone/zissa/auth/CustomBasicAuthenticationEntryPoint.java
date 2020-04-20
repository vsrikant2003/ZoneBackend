package com.zone.zissa.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

/**
 * The CustomBasicAuthenticationEntryPoint Class.
 */
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomBasicAuthenticationEntryPoint.class);

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response,
            final AuthenticationException authException) throws IOException, ServletException {
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter writer = response.getWriter();
        JSONObject jsonobject = new JSONObject();
        try {
            jsonobject.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            jsonobject.put("message", "Bad Credentials");
        } catch (JSONException e) {
            LOGGER.info("Json Exception",e);
        }
        writer.println(jsonobject.toString());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("MY_TEST_REALM");
        super.afterPropertiesSet();
    }
}
