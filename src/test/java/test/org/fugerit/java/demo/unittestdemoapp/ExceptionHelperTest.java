package test.org.fugerit.java.demo.unittestdemoapp;

import org.fugerit.java.demo.unittestdemoapp.ExceptionHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExceptionHelperTest {

    @Test
    void testException() {
        Assertions.assertEquals("Error processing document, error:Scenario Ex",
                ExceptionHelper.DEFAULT.apply(new Exception("Scenario Ex")).getMessage());
    }

}
