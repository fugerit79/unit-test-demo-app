package org.fugerit.java.demo.unittestdemoapp.security;

import io.quarkus.security.identity.IdentityProviderManager;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.AuthenticationRequest;
import io.quarkus.vertx.http.runtime.security.ChallengeData;
import io.quarkus.vertx.http.runtime.security.HttpAuthenticationMechanism;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import jakarta.json.JsonObject;
import java.util.Collections;
import java.util.Set;

@ApplicationScoped
@Slf4j
public class JwtAuthenticationMechanism implements HttpAuthenticationMechanism {

    UnsecuredJwtParser jwtParser;

    public JwtAuthenticationMechanism(UnsecuredJwtParser jwtParser) {
        this.jwtParser = jwtParser;
    }

    @Override
    public Uni<SecurityIdentity> authenticate(RoutingContext context, IdentityProviderManager identityProviderManager) {
        String authHeader = context.request().getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.debug("No Bearer token found");
            return Uni.createFrom().nullItem();
        }

        String token = authHeader.substring(7);

        try {
            // Parse JWT (senza validazione - già fatta dal gateway)
            JsonObject claims = jwtParser.parseToken(token);
            String username = claims.getString("sub", null);

            if (username == null) {
                log.warn("No subject found in JWT");
                return Uni.createFrom().nullItem();
            }

            log.info("JWT Mechanism - Extracted principal: {}", username);

            // Crea la request e DELEGA all'IdentityProviderManager
            // Questo farà sì che l'augmentor venga invocato
            JwtAuthenticationRequest authRequest = new JwtAuthenticationRequest(username, token);
            return identityProviderManager.authenticate(authRequest);

        } catch (Exception e) {
            log.error("Error processing JWT", e);
            return Uni.createFrom().nullItem();
        }
    }

    @Override
    public Uni<ChallengeData> getChallenge(RoutingContext context) {
        return Uni.createFrom().item(
                new ChallengeData(401, "WWW-Authenticate", "Bearer"));
    }

    @Override
    public Set<Class<? extends AuthenticationRequest>> getCredentialTypes() {
        return Collections.singleton(JwtAuthenticationRequest.class);
    }
}