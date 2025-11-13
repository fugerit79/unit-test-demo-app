package org.fugerit.java.demo.unittestdemoapp.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.demo.unittestdemoapp.util.EnumErrori;
import org.fugerit.java.demo.unittestdemoapp.util.ResponseHelper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

@Slf4j
@ApplicationScoped
public class JwtHelper {

    ResponseHelper responseHelper;

    public JwtHelper(ResponseHelper responseHelper) {
        this.responseHelper = responseHelper;
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public String getSubjectWithoutVerification(String token) {
        // Rimuovi "Bearer " se presente
        token = token.replace("Bearer ", "");

        // Il JWT è composto da: header.payload.signature
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            log.error(EnumErrori.INVALID_JWT.getDescription());
            throw this.responseHelper.createWebApplicationException400(EnumErrori.INVALID_JWT);
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
            log.error(EnumErrori.INVALID_JWT_PAYLOAD.getDescription(), e);
            throw this.responseHelper.createWebApplicationException400(EnumErrori.INVALID_JWT_PAYLOAD);
        }
    }

    public void setupUser(String sub, UserInfo userInfo) {
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
