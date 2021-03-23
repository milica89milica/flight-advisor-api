package com.htec.fa_api.util;

public enum DaylightSavingsTime {
    E("Europe"),
    A("US/Canada"),
    S("South America"),
    O("Australia"),
    Z("New Zeland"),
    N("None"),
    U("Unknown");

    public final String label;

    DaylightSavingsTime(String label) {
        this.label = label;
    }
}
