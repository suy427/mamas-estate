package com.sondahum.mamas.domain.estate;

import java.util.LinkedHashMap;

public enum Status {
    ONSALE(1, "ONSALE")
    , UNDERSALE(2, "UNDERSALE")
    , SOLD(3, "SOLD");

    private final int value;
    private final String name;
    private static final LinkedHashMap<Integer, Status> valueMap = new LinkedHashMap<Integer, Status>();
    private static final LinkedHashMap<String, Status> nameMap = new LinkedHashMap<String, Status>();

    static {
        for (Status status : values()) {
            valueMap.put(status.value, status);
            nameMap.put(status.name, status);
        }
    }

    Status(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Status findByValue(int value) {
        return valueMap.get(value);
    }

    public static Status findByName(String name) {
        return nameMap.get(name);
    }


}
