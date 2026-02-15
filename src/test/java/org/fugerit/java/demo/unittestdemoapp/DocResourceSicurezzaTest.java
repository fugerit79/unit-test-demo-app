package org.fugerit.java.demo.unittestdemoapp;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
/*
 * questa test suite contiene due tipi di test :
 *
 * 1. usando @TestSecurity, annotation di quarkus con cui vengono iniettati utente e ruolo
 * 2. iniettando un JWT direttamente con RestAssured .header("Authorization", "Bearer " + "${JWT}")
 */
class DocResourceSicurezzaTest {

    @Test
    @Tag("security")
    @Tag("authorized")
    @Tag("TestSecurity")
    @TestSecurity(user = "USER2", roles = { "user" })
    void testHtmlOkNoAdminRole() {
        // a questo path sono autorizzati anche gli utenti con semplice ruolo 'user'
        given()
                .when().get("/doc/example.html").then().statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Tag("security")
    @Tag("authorized")
    @Tag("TestSecurity")
    @TestSecurity(user = "USER2", roles = { "user", "admin" })
    void testPdfOkNoAdminRole() {
        // a questo path sono autorizzati anche gli utenti con semplice ruolo 'user'
        given().when().get("/doc/example.pdf").then().statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Tag("security")
    @Tag("unauthorized")
    @Tag("TestSecurity")
    void testMarkdown401NoAuthorizationBearer() {
        given()
                .when().get("/doc/example.md").then().statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    @Tag("security")
    @Tag("forbidden")
    @Tag("TestSecurity")
    @TestSecurity(user = "USER1", roles = { "user" })
    void testMarkdown403NoAdminRole() {
        given()
                .when().get("/doc/example.pdf").then().statusCode(Response.Status.FORBIDDEN.getStatusCode());
    }

    @Test
    @Tag("security")
    @Tag("success")
    @Tag("Bearer")
    void testOkWithJwt() {
        given()
                .header("Authorization", String.format("Bearer %s", DemoJwtGeneratorRest.generateAdminToken()))
                .when().get("/doc/example.pdf").then().statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Tag("security")
    @Tag("forbidden")
    @Tag("Bearer")
    void testForbiddenWithJwt() {
        given()
                .header("Authorization", "Bearer %s".formatted(DemoJwtGeneratorRest.generateGuestToken()))
                .when().get("/doc/example.pdf").then().statusCode(Response.Status.FORBIDDEN.getStatusCode());
    }

    @Test
    @Tag("security")
    @Tag("unauthorized")
    @Tag("Bearer")
    void testUnauthorizedWithoutJwt() {
        given()
                .when().get("/doc/example.pdf").then().statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    @Tag("security")
    @Tag("unauthorized")
    @Tag("Bearer")
    void testUnauthorizedWithWrongJwt() {
        given()
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVU0VSMSIsIm5hbWUi")
                .when().get("/doc/example.pdf").then().statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    @Tag("security")
    @Tag("authorized")
    @Tag("Bearer")
    void testOkJwtMarkDown() {
        given()
                .header("Authorization", "Bearer %s".formatted(DemoJwtGeneratorRest.generateGuestToken()))
                .when().get("/doc/example.md").then().statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Tag("security")
    @Tag("authorized")
    @Tag("Bearer")
    void testOkJwtAsciiDoc() {
        given()
                .header("Authorization", "Bearer %s".formatted(DemoJwtGeneratorRest.generateAdminToken()))
                .when().get("/doc/example.adoc").then().statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Tag("security")
    @Tag("authorized")
    @Tag("Bearer")
    void testForbiddenJwtAsciiDoc() {
        given()
                .header("Authorization", "Bearer %s".formatted(new DemoJwtGeneratorRest().newToken( "guest" )))
                .when().get("/doc/example.pdf").then().statusCode(Response.Status.FORBIDDEN.getStatusCode());
    }

}