package com.ecoline.application.data;

public enum Role {
    USER("user"), ADMIN("admin"), WEIGHER_RUBBER("weigher_rubber"),
    WEIGHER_BULK("weigher_bulk"), WEIGHER_CHALK("weigher_chalk"),
    WEIGHER_CARBON("weigher_carbon"),TECHNOLOGIST("technologist"),
    MACHINIST("machinist"), ROLLERMAN("rollerman"), LABWORKER("labworker"),
    ROLE6("role6"), ROLE7("role7"), ;

    private String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

}
