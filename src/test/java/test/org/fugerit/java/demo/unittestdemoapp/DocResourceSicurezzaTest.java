package test.org.fugerit.java.demo.unittestdemoapp;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.ws.rs.core.Response;
import org.fugerit.java.demo.unittestdemoapp.auth.EnumRoles;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
class DocResourceSicurezzaTest {

    @Test
    @TestSecurity(user = StubUsers.USER1, roles = { EnumRoles.USER_CODE })
    void testHtmlOkNoAdminRole() {
        // a questo path sono autorizzati anche gli utenti con semplice ruolo 'user'
        given()
                .when().get("/doc/example.html").then().statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @TestSecurity(user = StubUsers.USER3)
    void testMarkdown403NoAuthorizationBearer() {
        given()
                .when().get("/doc/example.md").then().statusCode(Response.Status.FORBIDDEN.getStatusCode());
    }

    @Test
    @TestSecurity(user = StubUsers.USER1, roles = { EnumRoles.USER_CODE })
    void testMarkdown403NoAdminRole() {
        given()
                .when().get("/doc/example.md").then().statusCode(Response.Status.FORBIDDEN.getStatusCode());
    }

    @Test
    @TestSecurity(user = StubUsers.USER3)
    void testHtml403NoAuthorizationBearer() {
        given()
                .when().get("/doc/example.html").then().statusCode(Response.Status.FORBIDDEN.getStatusCode());
    }

    @Test
    @TestSecurity(user = StubUsers.USER3)
    void testAsciiDoc403NoAuthorizationBearer() {
        given()
                .when().get("/doc/example.adoc").then().statusCode(Response.Status.FORBIDDEN.getStatusCode());
    }

}