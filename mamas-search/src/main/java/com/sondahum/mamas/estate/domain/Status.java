package com.sondahum.mamas.estate.domain;

import java.util.LinkedHashMap;

public enum Status {
    ONSALE(1, "ONSALE"), UNDERSALE(2, "UNDERSALE"), SOLD(3, "SOLD");

    Status(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Status findByValue(int value) {
        return ((Status) (valueMap.getAt(value)));
    }

    public static Status findByName(String name) {
        return ((Status) (nameMap.getAt(name)));
    }

    private final int value;
    private final String name;
    private static final LinkedHashMap<Integer, Status> valueMap = new LinkedHashMap<Integer, Status>();
    private static final LinkedHashMap<String, Status> nameMap = new LinkedHashMap<String, Status>();
}
