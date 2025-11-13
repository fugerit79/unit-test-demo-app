package test.org.fugerit.java.demo.unittestdemoapp;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
class DocResourceSicurezzaTest {

    @Test
    void testHtmlOkNoAdminRole() {
        // a questo path sono autorizzati anche gli utenti con semplice ruolo 'user'
        given()
                .header("Authorization", "Bearer " + DocResourceTest.JWT_USER2)
                .when().get("/doc/example.html").then().statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void testInvalidJwt() {
        given()
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVU0VSMSIsIm5hbWUi")
                .when().get("/doc/example.md").then().statusCode(Response.Status.BAD_REQUEST.getStatusCode())
                .body(containsString("400001"));
    }

    @Test
    void testInvalidJwtPayload() {
        given()
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVU0VSMSIsIm5hbWUi.signature")
                .when().get("/doc/example.md").then().statusCode(Response.Status.BAD_REQUEST.getStatusCode())
                .body(containsString("400002"));
    }

    @Test
    void testMarkdown401NoAuthorizationBearer() {
        given()
                .when().get("/doc/example.md").then().statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    void testMarkdown401NoAdminRole() {
        given()
                .header("Authorization", "Bearer " + DocResourceTest.JWT_USER2)
                .when().get("/doc/example.md").then().statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    void testHtml401NoAuthorizationBearer() {
        given()
                .when().get("/doc/example.html").then().statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    void testAsciiDocNoAuthorizationBearer() {
        given()
                .when().get("/doc/example.adoc").then().statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

}