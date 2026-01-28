package org.fugerit.java.demo.unittestdemoapp.security;

import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.IdentityProvider;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class JwtIdentityProvider implements IdentityProvider<JwtAuthenticationRequest> {

    @Override
    public Class<JwtAuthenticationRequest> getRequestType() {
        return JwtAuthenticationRequest.class;
    }

    @Override
    public Uni<SecurityIdentity> authenticate(JwtAuthenticationRequest request, AuthenticationRequestContext context) {
        log.info("Identity Provider - Creating identity for: {}", request.getUsername());

        // Crea una SecurityIdentity base con SOLO il principal
        // I ruoli verranno aggiunti dal LdapSecurityIdentityAugmentor
        QuarkusSecurityIdentity.Builder builder = QuarkusSecurityIdentity.builder();
        builder.setPrincipal(request::getUsername);
        builder.setAnonymous(false);

        // IMPORTANTE: Ritorna l'identity tramite Uni
        // L'augmentor verrà automaticamente invocato da Quarkus
        return Uni.createFrom().item(builder.build());
    }
}