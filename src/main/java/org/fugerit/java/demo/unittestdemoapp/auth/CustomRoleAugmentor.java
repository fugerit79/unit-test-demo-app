package org.fugerit.java.demo.unittestdemoapp.auth;

import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.SecurityIdentityAugmentor;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import lombok.extern.slf4j.Slf4j;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
@Slf4j
public class CustomRoleAugmentor implements SecurityIdentityAugmentor {

    RequestDataCapturer requestDataCapturer;

    public CustomRoleAugmentor(RequestDataCapturer requestDataCapturer) {
        this.requestDataCapturer = requestDataCapturer;
    }

    @Override
    @ActivateRequestContext
    public Uni<SecurityIdentity> augment(SecurityIdentity identity, AuthenticationRequestContext context) {
        UserInfo userInfo = this.requestDataCapturer.getUserInfo();
        Set<String> ruoliDaAggiungere = userInfo.getRoles().stream().map(EnumRoles::getCode).collect(Collectors.toSet());
        QuarkusSecurityIdentity.Builder builder = QuarkusSecurityIdentity.builder(identity);
        builder.setPrincipal(new Principal() {
            @Override
            public String getName() {
                return userInfo.getSub();
            }
        });
        builder.addRoles(ruoliDaAggiungere);
        return Uni.createFrom().item(builder.build());
    }
}