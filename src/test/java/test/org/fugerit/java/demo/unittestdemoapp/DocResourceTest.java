package test.org.fugerit.java.demo.unittestdemoapp;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

@QuarkusTest
class DocResourceTest {

    public static final String JWT_USER1 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVU0VSMSIsIm5hbWUiOiJNYXJpZSBDdXJpZSIsImdpdmVuTmFtZSI6Ik1hcmllIiwic24iOiJDdXJpZSIsInJvbGVzIjpbImFkbWlucyIsInVzZXJzIiwic2NpZW50aXN0cyJdLCJpYXQiOjE3MzE0MjcyMDAsImV4cCI6MTczMTUxMzYwMH0.9kVrJ8FYmqxJ0_sZWxZ5QXN7vH8KGPdQzRp3YJ6xLMw";

    public static final String JWT_USER2 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVU0VSMiIsIm5hbWUiOiJQaWVycmUgQ3VyaWUiLCJnaXZlbk5hbWUiOiJQaWVycmUiLCJzbiI6IkN1cmllIiwicm9sZXMiOlsidXNlcnMiLCJzY2llbnRpc3RzIl0sImlhdCI6MTczMTQyNzIwMCwiZXhwIjoxNzMxNTEzNjAwfQ.8Z_xM3jYqNkL5TfVrWpE2HnJ9sQ6RtU4oPbKcA7wXeI";

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
    void testMarkdownOk() {
        given()
                .header("Authorization", "Bearer " + JWT_USER1)
                .when().get("/doc/example.md").then().statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void testMarkdown401NoAuthorizationBearer() {
        given()
                .when().get("/doc/example.md").then().statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    void testMarkdown401NoAdminRole() {
        given()
                .header("Authorization", "Bearer " + JWT_USER2)
                .when().get("/doc/example.md").then().statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    void testHtmlOk() {
        given()
                .header("Authorization", "Bearer " + JWT_USER1)
                .when().get("/doc/example.html").then().statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void testHtml401NoAuthorizationBearer() {
        given()
                .when().get("/doc/example.html").then().statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    void testHtmlOkNoAdminRole() {
        // a questo path sono autorizzati anche gli utenti con semplice ruolo 'user'
        given()
                .header("Authorization", "Bearer " + JWT_USER2)
                .when().get("/doc/example.html").then().statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void testAsciiDocOk() {
        given()
                .header("Authorization", "Bearer " + JWT_USER1)
                .when().get("/doc/example.adoc").then().statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void testAsciiDocNoAuthorizationBearer() {
        given()
                .when().get("/doc/example.adoc").then().statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

}