package org.fugerit.java.demo.unittestdemoapp.util;

import lombok.ToString;

@ToString
public enum EnumErrori {

    GENERIC_ERROR(500001, "Errore interno"),
    INVALID_JWT(400001, "JWT Non Valido"),
    INVALID_JWT_PAYLOAD(400002, "Payload JWT Non valido");

    private final Integer code;
    private final String description;

    EnumErrori(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
