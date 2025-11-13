package org.fugerit.java.demo.unittestdemoapp.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fugerit.java.core.cfg.ConfigRuntimeException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

public class JwtHelper {

    private JwtHelper() {
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String getSubjectWithoutVerification(String token) {
        // Rimuovi "Bearer " se presente
        token = token.replace("Bearer ", "");

        // Il JWT è composto da: header.payload.signature
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT token");
        }

        // Decodifica il payload (parte 2)
        String payloadJson = new String(
                Base64.getUrlDecoder().decode(parts[1]),
                StandardCharsets.UTF_8);
        try {
            // Parsing JSON
            Map<String, Object> payload = MAPPER.readValue(payloadJson, Map.class);
            return (String) payload.get("sub");
        } catch (Exception e) {
            throw new ConfigRuntimeException("Error parsing JWT payload", e);
        }
    }

    public static void setupUser(String sub, UserInfo userInfo) {
        userInfo.setSub(sub);
        if ("USER1".equalsIgnoreCase(sub)) {
            userInfo.setRoles(Arrays.asList(EnumRoles.USER, EnumRoles.ADMIN));
        } else if ("USER2".equalsIgnoreCase(sub)) {
            userInfo.setRoles(Arrays.asList(EnumRoles.USER));
        } else {
            userInfo.setRoles(new ArrayList<>());
        }
    }

}
