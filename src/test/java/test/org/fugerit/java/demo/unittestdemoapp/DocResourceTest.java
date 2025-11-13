package test.org.fugerit.java.demo.unittestdemoapp;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.ws.rs.core.Response;
import org.fugerit.java.demo.unittestdemoapp.auth.EnumRoles;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

@QuarkusTest
class DocResourceTest {

    @Test
    @TestSecurity(user = StubUsers.USER1, roles = { EnumRoles.ADMIN_CODE, EnumRoles.USER_CODE })
    void testMarkdownOk() {
        given()
                .when().get("/doc/example.md").then().statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @TestSecurity(user = StubUsers.USER1, roles = { EnumRoles.ADMIN_CODE, EnumRoles.USER_CODE })
    void testHtmlOk() {
        given()
                .when().get("/doc/example.html").then().statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @TestSecurity(user = StubUsers.USER1, roles = { EnumRoles.ADMIN_CODE, EnumRoles.USER_CODE })
    void testAsciiDocOk() {
        given()
                .when().get("/doc/example.adoc").then().statusCode(Response.Status.OK.getStatusCode());
    }

}