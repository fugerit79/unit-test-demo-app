package org.fugerit.java.demo.unittestdemoapp.auth;

import io.vertx.core.http.HttpServerRequest;
import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.demo.unittestdemoapp.util.EnumErrori;
import org.fugerit.java.demo.unittestdemoapp.util.ResponseHelper;

import java.lang.reflect.Method;
import java.util.Arrays;

@Interceptor
@AuthRoles
@Priority(value = 2)
@Slf4j
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
            EnumRoles[] roles = m.getAnnotation(AuthRoles.class).roles();

            String authorization = request.getHeader("Authorization");
            if (StringUtils.isNotEmpty(authorization)) {
                String sub = this.jwtHelper.getSubjectWithoutVerification(authorization);
                this.jwtHelper.setupUser(sub, userInfo);
                this.userInfo.setSub(sub);
                log.info("check roles {}", Arrays.asList(roles));
                log.info("user  roles {}", this.userInfo.getRoles());
                for (EnumRoles role : roles) {
                    for (EnumRoles current : this.userInfo.getRoles()) {
                        if (current.getCode().equals(role.getCode())) {
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
