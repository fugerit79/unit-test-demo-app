package org.fugerit.java.demo.unittestdemoapp.security;

import io.quarkus.security.identity.request.BaseAuthenticationRequest;

public class JwtAuthenticationRequest extends BaseAuthenticationRequest {

    private final String username;
    private final String token;

    public JwtAuthenticationRequest(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}