package org.fugerit.java.demo.unittestdemoapp.auth;

import jakarta.enterprise.util.Nonbinding;
import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.*;

@Inherited
@InterceptorBinding
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthRoles {

    @Nonbinding
    EnumRoles[] roles() default EnumRoles.USER;

}
