package org.fugerit.java.demo.unittestdemoapp;

import jakarta.ws.rs.WebApplicationException;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public class ExceptionHelper {

    private ExceptionHelper() {
    }

    public static final Function<Exception, WebApplicationException> DEFAULT = e -> {
        String message = String.format("Error processing document, error:%s", e);
        log.error(message, e);
        return new WebApplicationException(message, e);
    };

}
