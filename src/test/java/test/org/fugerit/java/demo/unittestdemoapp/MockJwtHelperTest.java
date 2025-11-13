package test.org.fugerit.java.demo.unittestdemoapp;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.demo.unittestdemoapp.auth.JwtHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
class MockJwtHelperTest {

    @InjectMock
    JwtHelper jwtHelper;

    /**
     * Se la gestione dell'errore è fatta correttamente, può essere complicato testare l'eccezione generica.
     *
     * Ad esempio in : AuthInterceptor
     *
     * } catch (WebApplicationException e) {
     * return e.getResponse();
     * } catch (Exception e) {
     * String message = String.format("Errore : %s", e.getMessage());
     * log.error(message, e);
     * return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(EnumErrori.GENERIC_ERROR).build();
     * }
     *
     * Andiamo a usare Mockito e QuarkusMock
     */
    @Test
    void testGenericErrorWithMockGeneric() {
        Mockito.doThrow(
                new ConfigRuntimeException("Mock exception")).when(jwtHelper).getSubjectWithoutVerification(any());

        // Esegui il test
        given()
                .header("Authorization", "Bearer " + DocResourceTest.JWT_USER1)
                .when()
                .get("/doc/example.md")
                .then()
                .statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .body(containsString("1"));
    }

}