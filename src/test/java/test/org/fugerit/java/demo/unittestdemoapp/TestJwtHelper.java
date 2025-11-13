package test.org.fugerit.java.demo.unittestdemoapp;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.demo.unittestdemoapp.auth.JwtHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestJwtHelper {

    @Test
    void testInvalidJwt() {
        String jwtWith2Parts = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVU0VSMSIsIm5hbWUiOiJNYXJpZSBDdXJpZSIsImdpdmVuTmFtZSI6Ik1hcmllIiwic24iOiJDdXJpZSIsInJvbGVzIjpbImFkbWlucyIsInVzZXJzIiwic2NpZW50aXN0cyJdLCJpYXQiOjE3MzE0MjcyMDAsImV4cCI6MTczMTUxMzYwMH0";
        Assertions.assertThrows( IllegalArgumentException.class, () -> {
            JwtHelper.getSubjectWithoutVerification( jwtWith2Parts );
        } );
    }

    @Test
    void testInvalidJwtPayload() {
        String invalidJwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVU0VSMSIsIm5hbWUi.signature";
        Assertions.assertThrows( ConfigRuntimeException.class, () -> {
            JwtHelper.getSubjectWithoutVerification( invalidJwt );
        } );
    }

}
