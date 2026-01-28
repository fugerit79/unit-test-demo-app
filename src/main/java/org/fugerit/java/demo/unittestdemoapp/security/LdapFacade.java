package org.fugerit.java.demo.unittestdemoapp.security;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * Si tratta di uno stub, andrebbe sostituita con una implementazione reale
 */
@ApplicationScoped
public class LdapFacade {

    private static final Map<String, Set<String>> LDAP_USERS_MOCK = new HashMap<>();
    static {
        // mappatura stub utenti
        LDAP_USERS_MOCK.put("USER1", Set.of(EnumRoles.USER.getCode()));
        LDAP_USERS_MOCK.put("USER2", Set.of(EnumRoles.USER.getCode(), EnumRoles.ADMIN.getCode()));
    }

    public Set<String> getRoles(String username) {
        return LDAP_USERS_MOCK.get(username);
    }

}
