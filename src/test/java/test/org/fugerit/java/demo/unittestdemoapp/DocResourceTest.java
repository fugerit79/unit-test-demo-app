package test.org.fugerit.java.demo.unittestdemoapp;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.ws.rs.core.Response;
import org.fugerit.java.demo.unittestdemoapp.auth.EnumRoles;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

@QuarkusTest
class DocResourceTest {

    /* Utente admin + user
 {
  "sub": "USER1",
  "roles": [
    "admin",
    "user"
  ],
  "iat": 1731500000,
  "exp": 1731503600,
  "iss": "unit-test-demo"
}
     */
    public static final String JWT_USER1 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVU0VSMSIsInJvbGVzIjpbImFkbWluIiwidXNlciJdLCJpYXQiOjE3MzE1MDAwMDAsImV4cCI6MTczMTUwMzYwMCwiaXNzIjoidW5pdC10ZXN0LWRlbW8ifQ.jYYIuJdnL0KTkt4MmgqUK8d6OV2NQbN7ZXcRC4wQVRs";

    // utente USER
    public static final String JWT_USER2 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVU0VSMiIsIm5hbWUiOiJQaWVycmUgQ3VyaWUiLCJnaXZlbk5hbWUiOiJQaWVycmUiLCJzbiI6IkN1cmllIiwicm9sZXMiOlsidXNlcnMiLCJzY2llbnRpc3RzIl0sImlhdCI6MTczMTQyNzIwMCwiZXhwIjoxNzMxNTEzNjAwfQ.8Z_xM3jYqNkL5TfVrWpE2HnJ9sQ6RtU4oPbKcA7wXeI";

    @Test
    @TestSecurity(user = "USER1", roles = {EnumRoles.ADMIN_CODE, EnumRoles.USER_CODE})
    void testMarkdownOk() {
        given()
                .when().get("/doc/example.md").then().statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void testHtmlOk() {
        given()
                .header("Authorization", "Bearer " + JWT_USER1)
                .when().get("/doc/example.html").then().statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void testAsciiDocOk() {
        given()
                .header("Authorization", "Bearer " + JWT_USER1)
                .when().get("/doc/example.adoc").then().statusCode(Response.Status.OK.getStatusCode());
    }

}