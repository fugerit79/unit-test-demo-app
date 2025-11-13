package org.fugerit.java.demo.unittestdemoapp.auth;

import io.vertx.core.http.HttpServerRequest;
import jakarta.enterprise.context.RequestScoped;
import org.fugerit.java.core.lang.helpers.StringUtils;

@RequestScoped
public class RequestDataCapturer {

    JwtHelper jwtHelper;

    HttpServerRequest request;

    UserInfo userInfo;

    public RequestDataCapturer(JwtHelper jwtHelper, UserInfo userInfo, HttpServerRequest request) {
        this.jwtHelper = jwtHelper;
        this.userInfo = userInfo;
        this.request = request;
    }

    public UserInfo getUserInfo() {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(authorization)) {
            String sub = this.jwtHelper.getSubjectWithoutVerification(authorization);
            this.jwtHelper.setupUser(sub, this.userInfo);
        }
        return this.userInfo;
    }

}
