package org.fugerit.java.demo.unittestdemoapp;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.fugerit.java.demo.unittestdemoapp.security.EnumRoles;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

@Slf4j
@ApplicationScoped
@Path("/demo")
public class DemoJwtGeneratorRest {

    private static final String ISSUER = "https://unittestdemoapp.fugerit.org";

    @APIResponse(responseCode = "201", description = "Generazione del JWT")
    @Tag(name = "jwt authorization demo")
    @Operation(operationId = "adminToken", summary = "Genera un nuovo JWT, i ruoli vanno passati come path param, separati da una virgola. (es. 'admin,user,guest')", description = "Attenzione : da utilizzare solo per motivi dimostrativi!")
    @GET
    @Produces("text/plain")
    @Path("/{roles}.txt")
    public String newToken(@PathParam("roles") String roles) {
        return generateToken("DEMOUSER", roles.split(","));
    }

    /**
     * Genera un JWT per un utente con ruolo guest
     *
     * return il token JWT generato
     */
    public static String generateGuestToken() {
        String[] roles = { EnumRoles.GUEST.getCode() };
        return generateToken("USER3", roles);
    }

    /**
     * Genera un JWT per un utente con ruoli user e guest
     *
     * return il token JWT generato
     */
    public static String generateUserToken() {
        String[] roles = { EnumRoles.USER.getCode(), EnumRoles.GUEST.getCode() };
        return generateToken("USER1", roles);
    }

    /**
     * Genera un JWT per un utente con ruoli admin, user e guest
     *
     * return il token JWT generato
     */
    public static String generateAdminToken() {
        String[] roles = { EnumRoles.ADMIN.getCode(), EnumRoles.USER.getCode(), EnumRoles.GUEST.getCode() };
        return generateToken("USER2", roles);
    }

    /**
     * Genera un JWT personalizzato
     *
     * @param username lo username da usare per il JWT (verrà inserito come upn e claim sub)
     * @param roles l'elenco dei ruoli da associare all'utente
     *
     *        return il token JWT generato
     */
    public static String generateToken(String username, String... roles) {
        return Jwt.issuer(ISSUER)
                .upn(username)
                .groups(new HashSet<>(Arrays.asList(roles)))
                .claim(Claims.sub.name(), username)
                .sign();
    }

}