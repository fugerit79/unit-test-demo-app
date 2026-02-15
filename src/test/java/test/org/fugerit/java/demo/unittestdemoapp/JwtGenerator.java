package test.org.fugerit.java.demo.unittestdemoapp;

import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;
import org.fugerit.java.demo.unittestdemoapp.security.EnumRoles;

import java.util.Arrays;
import java.util.HashSet;

public class JwtGenerator {

    private static final String ISSUER = "https://unittestdemoapp.fugerit.org";

    /**
     * Genera un JWT per un utente con ruolo USER
     */
    public static String generateForbiddenToken() {
        String[] roles = { EnumRoles.GUEST.getCode() };
        return generateToken("USER3", roles);
    }

    /**
     * Genera un JWT per un utente con ruolo USER
     */
    public static String generateUserToken() {
        String[] roles = { EnumRoles.USER.getCode() };
        return generateToken("USER1", roles);
    }

    /**
     * Genera un JWT per un utente con ruoli USER e ADMIN
     */
    public static String generateAdminToken() {
        String[] roles = { EnumRoles.ADMIN.getCode(), EnumRoles.USER.getCode() };
        return generateToken("USER2", roles);
    }

    /**
     * Genera un JWT personalizzato
     */
    public static String generateToken(String username, String... roles) {
        return Jwt.issuer(ISSUER)
                .upn(username)
                .groups(new HashSet<>(Arrays.asList(roles)))
                .claim(Claims.sub.name(), username)
                .sign();
    }
}