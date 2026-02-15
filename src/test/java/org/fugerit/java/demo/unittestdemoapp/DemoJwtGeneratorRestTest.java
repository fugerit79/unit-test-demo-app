package org.fugerit.java.demo.unittestdemoapp;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class DemoJwtGeneratorRestTest {

    @Test
    @Tag("demo")
    void testDemoAdminToken() {
        given()
                .when().get("/demo/admin,user,guest.txt").then().statusCode(Response.Status.OK.getStatusCode());
    }

}
