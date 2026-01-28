package test.org.fugerit.java.demo.unittestdemoapp;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.demo.unittestdemoapp.security.UnsecuredJwtParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestUnsecuredJwtParser {

    @Test
    void testOk() {
        String jwt = DocResourceTest.JWT_USER1;
        UnsecuredJwtParser parser = new UnsecuredJwtParser();
        String username = parser.getSubject(jwt);
        Assertions.assertEquals("USER1", username);
    }

    @Test
    void testWrongJwt() {
        String jwt = "wrong jwt";
        UnsecuredJwtParser parser = new UnsecuredJwtParser();
        Assertions.assertThrows(ConfigRuntimeException.class, () -> parser.getSubject(jwt));
    }

    @Test
    void testJwtKo1() {
        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVU0VSMSIsIm5hbWUi";
        UnsecuredJwtParser parser = new UnsecuredJwtParser();
        Assertions.assertThrows(ConfigRuntimeException.class, () -> parser.getSubject(jwt));
    }

}
