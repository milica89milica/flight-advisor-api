package com.htec.fa_api.util;

public enum ActionType {
    CREATE("create"),
    UPDATE("update"),
    DELETE("delete"),
    PASSWORD_CHANGE("password change");

    private String text;

    ActionType() {
    }

    ActionType(String text) {
        this.text = text;
    }
}
