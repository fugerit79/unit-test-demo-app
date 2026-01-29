package test.org.fugerit.java.demo.unittestdemoapp;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

@QuarkusTest
class DocResourceTest {

    public static final String JWT_USER1 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVU0VSMSIsIm5hbWUiOiJNYXJpZSBDdXJpZSIsImdpdmVuTmFtZSI6Ik1hcmllIiwic24iOiJDdXJpZSIsInJvbGVzIjpbImFkbWlucyIsInVzZXJzIiwic2NpZW50aXN0cyJdLCJpYXQiOjE3MzE0MjcyMDAsImV4cCI6MTczMTUxMzYwMH0.9kVrJ8FYmqxJ0_sZWxZ5QXN7vH8KGPdQzRp3YJ6xLMw";

    public static final String JWT_USER2 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVU0VSMiIsIm5hbWUiOiJQaWVycmUgQ3VyaWUiLCJnaXZlbk5hbWUiOiJQaWVycmUiLCJzbiI6IkN1cmllIiwicm9sZXMiOlsidXNlcnMiLCJzY2llbnRpc3RzIl0sImlhdCI6MTczMTQyNzIwMCwiZXhwIjoxNzMxNTEzNjAwfQ.8Z_xM3jYqNkL5TfVrWpE2HnJ9sQ6RtU4oPbKcA7wXeI";

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