package org.fugerit.java.demo.unittestdemoapp.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.cfg.ConfigRuntimeException;

import java.io.StringReader;
import java.util.Base64;

@ApplicationScoped
@Slf4j
public class UnsecuredJwtParser {

    public JsonObject parseToken(String token) {
        try {
            // Split JWT into parts
            String[] parts = token.split("\\.");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid JWT format");
            }

            // Decode payload (second part)
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            log.debug("JWT payload: {}", payload);

            // Parse as JSON
            return Json.createReader(new StringReader(payload)).readObject();

        } catch (Exception e) {
            log.error("Failed to parse JWT token", e);
            throw new ConfigRuntimeException("Invalid JWT token", e);
        }
    }

    public String getSubject(String token) {
        JsonObject claims = parseToken(token);
        return claims.getString("sub", null);
    }
}