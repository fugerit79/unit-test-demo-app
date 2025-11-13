package org.fugerit.java.demo.unittestdemoapp.auth;

import io.vertx.core.http.HttpServerRequest;
import jakarta.annotation.Priority;
import jakarta.annotation.security.RolesAllowed;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.demo.unittestdemoapp.util.EnumErrori;

import java.lang.reflect.Method;
import java.util.Arrays;

@Interceptor
@AuthRoles
@Priority(value = 2)
@Slf4j
/**
 * Interceptor per gestire i ruoli di autorizzazione
 *
 * Usa la annotation standard :
 * jakarta.annotation.security.RolesAllowed
 *
 * Ecco un esempio di utilizzo :
 *
 * @SecurityRequirement(name = "bearerAuth")
 *                           @RolesAllowed( {ADMIN_CODE, USER_CODE} )
 *                           public Response htmlExample() {
 *                           return Response.status(Response.Status.OK).entity(processDocument(DocConfig.TYPE_HTML)).build();
 *                           }
 *
 */
public class AuthInterceptor {

    JwtHelper jwtHelper;

    HttpServerRequest request;

    UserInfo userInfo;

    public AuthInterceptor(JwtHelper jwtHelper, UserInfo userInfo, HttpServerRequest request) {
        this.jwtHelper = jwtHelper;
        this.userInfo = userInfo;
        this.request = request;
    }

    @AroundInvoke
    public Object check(InvocationContext ctx) {
        try {
            Method m = ctx.getMethod();
            String[] roles = m.getAnnotation(RolesAllowed.class).value();

            String authorization = request.getHeader("Authorization");
            if (StringUtils.isNotEmpty(authorization)) {
                String sub = this.jwtHelper.getSubjectWithoutVerification(authorization);
                this.jwtHelper.setupUser(sub, userInfo);
                this.userInfo.setSub(sub);
                log.info("check roles {}", Arrays.asList(roles));
                log.info("user  roles {}", this.userInfo.getRoles());
                for (String role : roles) {
                    for (EnumRoles current : this.userInfo.getRoles()) {
                        if (current.getCode().equals(role)) {
                            return ctx.proceed();
                        }
                    }
                }
            }
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (WebApplicationException e) {
            return e.getResponse();
        } catch (Exception e) {
            String message = String.format("Errore : %s", e.getMessage());
            log.error(message, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(EnumErrori.GENERIC_ERROR).build();
        }
    }

}
