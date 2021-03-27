package com.htec.fa_api.security;

public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN");

    private String name;

    UserRole(String name) {
        this.name = name;
    }
}
