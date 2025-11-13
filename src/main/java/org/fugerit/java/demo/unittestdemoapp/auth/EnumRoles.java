package org.fugerit.java.demo.unittestdemoapp.auth;

public enum EnumRoles {

    ADMIN(1, "admin", "Amministratore"),
    USER(2, "user", "Utente");

    public static final String ADMIN_CODE = "admin";
    public static final String USER_CODE = "user";

    private Integer id;
    private String code;
    private String description;

    EnumRoles(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public Integer getId() {
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