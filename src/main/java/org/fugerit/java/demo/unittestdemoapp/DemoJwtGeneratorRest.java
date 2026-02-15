package org.fugerit.java.demo.unittestdemoapp;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.fugerit.java.demo.unittestdemoapp.security.EnumRoles;

import java.util.Arrays;
import java.util.HashSet;

@Slf4j
@ApplicationScoped
@Path("/demo")
public class DemoJwtGeneratorRest {

    private static final String ISSUER = "https://unittestdemoapp.fugerit.org";

    @APIResponse(responseCode = "201", description = "Generazione del JWT")
    @Tag(name = "jwt authorization demo")
    @Operation(operationId = "adminToken", summary = "Genera un nuovo Token con permessi di amministratore (admin).", description = "Attenzione : da utilizzare solo per motivi dimostrativi!")
    @GET
    @Produces("text/plain")
    @Path("/new-admin-jwt.txt")
    public Response newAdminToken() {
        return Response.status(Response.Status.CREATED).entity(generateAdminToken()).build();
    }

    @APIResponse(responseCode = "201", description = "Generazione del JWT")
    @Tag(name = "jwt authorization demo")
    @Operation(operationId = "userToken", summary = "Genera un nuovo Token con permessi di utente (user).", description = "Attenzione : da utilizzare solo per motivi dimostrativi!")
    @GET
    @Produces("text/plain")
    @Path("/new-user-jwt.txt")
    public Response newUserToken() {
        return Response.status(Response.Status.CREATED).entity(generateUserToken()).build();
    }

    @APIResponse(responseCode = "201", description = "Generazione del JWT")
    @Tag(name = "jwt authorization demo")
    @Operation(operationId = "guestToken", summary = "Genera un nuovo Token con permessi di ospite (guest).", description = "Attenzione : da utilizzare solo per motivi dimostrativi!")
    @GET
    @Produces("text/plain")
    @Path("/new-guest-jwt.txt")
    public Response newGuestToken() {
        return Response.status(Response.Status.CREATED).entity(generateGuestToken()).build();
    }

    /**
     * Genera un JWT per un utente con ruolo GUEST
     */
    public static String generateGuestToken() {
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