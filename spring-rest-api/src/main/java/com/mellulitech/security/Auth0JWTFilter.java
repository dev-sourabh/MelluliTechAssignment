package com.mellulitech.security;

import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class Auth0JWTFilter implements Filter {

    private String domain;
    private String audience;
    private JWTVerifier verifier;

    @Override
    public void init(FilterConfig filterConfig) {

        domain = filterConfig.getInitParameter("auth0.domain");
        audience = filterConfig.getInitParameter("auth0.audience");

        JwkProvider provider = new JwkProviderBuilder(domain).cached(10, 24, TimeUnit.HOURS).build();
        RSAKeyProvider keyProvider = new MyRSAKeyProvider(provider);
        verifier = JWT.require(Algorithm.RSA256(keyProvider))
                .withAudience(audience)
                .withIssuer("https://" + domain + "/")
                .build();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String authHeader = req.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        try {
            String token = authHeader.substring(7);
            DecodedJWT jwt = verifier.verify(token);
            request.setAttribute("user", jwt.getSubject());
            chain.doFilter(request, response);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Invalid token: " + e.getMessage());
        }
    }

}
