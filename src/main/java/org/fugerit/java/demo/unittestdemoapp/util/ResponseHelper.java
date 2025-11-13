package org.fugerit.java.demo.unittestdemoapp.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class ResponseHelper {

    public WebApplicationException createWebApplicationException400(EnumErrori errore) {
        return new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(errore).build());
    }

    public WebApplicationException createWebApplicationException500(EnumErrori errore) {
        return new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errore).build());
    }

}
