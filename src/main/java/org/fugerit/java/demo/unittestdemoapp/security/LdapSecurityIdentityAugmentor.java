package org.fugerit.java.demo.unittestdemoapp.security;

import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.SecurityIdentityAugmentor;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.ws.rs.Priorities;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@ApplicationScoped
@Slf4j
@ActivateRequestContext
public class LdapSecurityIdentityAugmentor implements SecurityIdentityAugmentor {

    LdapFacade facade;

    public LdapSecurityIdentityAugmentor(LdapFacade facade) {
        this.facade = facade;
    }

    @Override
    public int priority() {
        return Priorities.AUTHENTICATION;
    }

    @Override
    @ActivateRequestContext
    public Uni<SecurityIdentity> augment(SecurityIdentity identity, AuthenticationRequestContext context) {
        String username = identity.getPrincipal().getName();
        Set<String> roles = this.facade.getRoles(username);
        log.info("user : {}, roles : {}", username, roles);
        return Uni.createFrom().item(this.build(identity, username, roles).get());
    }

    @Override
    public Uni<SecurityIdentity> augment(SecurityIdentity identity, AuthenticationRequestContext context,
            Map<String, Object> attributes) {
        return SecurityIdentityAugmentor.super.augment(identity, context, attributes);
    }

    private Supplier<SecurityIdentity> build(SecurityIdentity identity, String user, Set<String> roles) {
        if (identity.isAnonymous()) {
            return () -> identity;
        } else {
            // create a new builder and copy principal, attributes, credentials and roles from the original identity
            QuarkusSecurityIdentity.Builder builder = QuarkusSecurityIdentity.builder(identity);
            builder.setPrincipal(() -> user);
            builder.addRoles(roles);
            return builder::build;
        }
    }

}
