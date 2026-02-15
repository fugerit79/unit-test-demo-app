package org.fugerit.java.demo.unittestdemoapp;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class DemoJwtGeneratorRestTest {

    @Test
    @Tag("demo")
    void testDemoAdminToken() {
        given()
                .when().get("/demo/new-admin-jwt.txt").then().statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    @Tag("demo")
    void testDemoUserToken() {
        given()
                .when().get("/demo/new-user-jwt.txt").then().statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    @Tag("demo")
    void testDemoGuestToken() {
        given()
                .when().get("/demo/new-guest-jwt.txt").then().statusCode(Response.Status.CREATED.getStatusCode());
    }

}
