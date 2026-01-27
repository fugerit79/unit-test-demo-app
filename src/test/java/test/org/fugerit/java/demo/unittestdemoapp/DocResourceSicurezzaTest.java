package test.org.fugerit.java.demo.unittestdemoapp;

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
 * 1. usando @TestSecurity, annotation di quarkus con cui vengono iniettati utente e ruolo, con un bypass del
 * SecurityIdentityAugmentor, https://github.com/quarkusio/quarkus/discussions/30411
 * 2. iniettando un JWT direttamente con RestAssured .header("Authorization", "Bearer " + DocResourceTest.JWT_USER1)
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
                .header("Authorization", "Bearer " + DocResourceTest.JWT_USER2)
                .when().get("/doc/example.pdf").then().statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Tag("security")
    @Tag("forbidden")
    @Tag("Bearer")
    void testForbiddenWithJwt() {
        given()
                .header("Authorization", "Bearer " + DocResourceTest.JWT_USER1)
                .when().get("/doc/example.pdf").then().statusCode(Response.Status.FORBIDDEN.getStatusCode());
    }

    @Test
    @Tag("security")
    @Tag("unauthorized")
    @Tag("Bearer")
    void testUnauthorizedWithJwt() {
        given()
                .when().get("/doc/example.pdf").then().statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

}