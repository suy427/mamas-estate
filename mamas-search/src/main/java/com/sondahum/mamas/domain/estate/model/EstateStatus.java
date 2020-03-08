package com.sondahum.mamas.domain.estate.model;

import java.util.LinkedHashMap;

public enum EstateStatus {
    ONSALE(1, "ONSALE")
    , UNDERSALE(2, "UNDERSALE")
    , SOLD(3, "SOLD");

    private final int value;
    private final String name;
    private static final LinkedHashMap<Integer, EstateStatus> valueMap = new LinkedHashMap<Integer, EstateStatus>();
    private static final LinkedHashMap<String, EstateStatus> nameMap = new LinkedHashMap<String, EstateStatus>();

    static {
        for (EstateStatus status : values()) {
            valueMap.put(status.value, status);
            nameMap.put(status.name, status);
        }
    }

    EstateStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static EstateStatus findByValue(int value) {
        return valueMap.get(value);
    }

    public static EstateStatus findByName(String name) {
        return nameMap.get(name);
    }


}
