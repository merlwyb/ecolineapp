package com.ecoline.application.data;

public enum Role {
    USER("user"), ADMIN("admin"), WEIGHER("weigher"), TECHNOLOGIST("technologist"),
    OPERATOR("operator"), ROLLERMAN("rollerman"), LABWORKER("labworker"),
    ROLE6("role6"), ROLE7("role7"), ;

    private String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

}
