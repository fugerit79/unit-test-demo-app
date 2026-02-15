package org.fugerit.java.demo.unittestdemoapp;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

@QuarkusTest
class DocResourceTest {

    @Test
    @Tag("business")
    @Tag("success")
    @TestSecurity(user = "USER2", roles = { "user", "admin" })
    void testMarkdownOk() {
        given()
                .when().get("/doc/example.md").then().statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Tag("business")
    @Tag("success")
    @TestSecurity(user = "USER1", roles = { "user" })
    void testHtmlOk() {
        given()
                .when().get("/doc/example.html").then().statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Tag("business")
    @Tag("success")
    @TestSecurity(user = "USER2", roles = { "user", "admin" })
    void testAsciiDocOk() {
        given()
                .when().get("/doc/example.adoc").then().statusCode(Response.Status.OK.getStatusCode());
    }

}