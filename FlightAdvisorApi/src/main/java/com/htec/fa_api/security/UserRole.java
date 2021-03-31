package com.htec.fa_api.security;

public enum UserRole { //authorities ROLE PREFIX
    USER("USER"),
    ADMIN("ADMIN");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
