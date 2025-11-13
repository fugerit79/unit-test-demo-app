package org.fugerit.java.demo.unittestdemoapp.auth;

import jakarta.enterprise.util.Nonbinding;
import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.*;

@Inherited
@InterceptorBinding
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
/**
 * Con questa annotation dichiaro quali ruoli sono necessari per
 * chiamare una certa API rest, ad esempio :
 *
 * @AuthRoles(roles = { ADMIN })
 *                  public Response asciidocExample() {
 *                  return Response.status(Response.Status.OK).entity(processDocument(DocConfig.TYPE_ADOC)).build();
 *                  }
 *
 *
 *                  E gestita dall' interceptor : AuthInterceptor
 */
public @interface AuthRoles {

    @Nonbinding
    EnumRoles[] roles() default EnumRoles.USER;

}
