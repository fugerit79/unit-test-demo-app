package test.org.fugerit.java.demo.unittestdemoapp;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.ws.rs.core.Response;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.demo.unittestdemoapp.DocHelper;
import org.fugerit.java.demo.unittestdemoapp.auth.EnumRoles;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
class MockDocResource500Test {

    @InjectMock
    DocHelper docHelper;

    @Test
    @TestSecurity(user = StubUsers.USER1, roles = { EnumRoles.ADMIN_CODE, EnumRoles.USER_CODE })
    void testGenericErrorWithMockGeneric() {
        Mockito.doThrow(
                new ConfigRuntimeException("Mock exception")).when(docHelper).getDocProcessConfig();

        // Esegui il test
        given()
                .when()
                .get("/doc/example.md")
                .then()
                .statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .body(containsString("1"));
    }

}