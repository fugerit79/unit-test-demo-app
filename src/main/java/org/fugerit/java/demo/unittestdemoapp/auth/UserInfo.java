package org.fugerit.java.demo.unittestdemoapp.auth;

import jakarta.enterprise.context.RequestScoped;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@RequestScoped
@ToString
public class UserInfo {

    @Getter
    @Setter
    private String sub;

    @Getter
    @Setter
    private List<EnumRoles> roles;

}
