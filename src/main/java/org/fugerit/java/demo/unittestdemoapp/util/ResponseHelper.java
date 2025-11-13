package org.fugerit.java.demo.unittestdemoapp.util;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.fugerit.java.core.function.SimpleValue;

public class ResponseHelper {

    private ResponseHelper() {
    }

    public static WebApplicationException createWebApplicationException400(EnumErrori errore) {
        return new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(errore).build());
    }

}
