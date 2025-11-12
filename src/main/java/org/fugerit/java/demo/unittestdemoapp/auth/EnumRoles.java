package org.fugerit.java.demo.unittestdemoapp.auth;

public enum EnumRoles {

    ADMIN("1", "admin", "Amministratore"),
    USER("2", "user", "Utente");

    private String id;
    private String code;
    private String description;

    EnumRoles(String id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "EnumRoles{" +
                "code='" + code + '\'' +
                ", id='" + id + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}