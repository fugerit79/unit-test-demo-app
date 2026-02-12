package test.org.fugerit.java.demo.unittestdemoapp;

import org.fugerit.java.demo.unittestdemoapp.util.EnumErrori;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EnumErroriTest {

    @Test
    void testEnumErrori() {
        EnumErrori ee = EnumErrori.GENERIC_ERROR;
        Assertions.assertEquals( 500001, ee.getCode() );
        Assertions.assertEquals( "Errore interno", ee.getDescription() );
    }

}
