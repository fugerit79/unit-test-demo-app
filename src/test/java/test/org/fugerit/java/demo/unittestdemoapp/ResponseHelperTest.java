package test.org.fugerit.java.demo.unittestdemoapp;

import org.fugerit.java.demo.unittestdemoapp.util.EnumErrori;
import org.fugerit.java.demo.unittestdemoapp.util.ResponseHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ResponseHelperTest {

    @Test
    void testResponseHelper() {
        ResponseHelper helper = new ResponseHelper();
        Assertions.assertEquals(500,
                helper.createWebApplicationException500(EnumErrori.GENERIC_ERROR).getResponse().getStatus());
        Assertions.assertEquals(400, helper.createWebApplicationException400(EnumErrori.INVALID_JWT).getResponse().getStatus());
    }

}
