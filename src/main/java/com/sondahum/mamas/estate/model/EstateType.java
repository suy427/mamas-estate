package com.sondahum.mamas.estate.model;

import java.util.LinkedHashMap;

public enum EstateType {
    APT(1, "APT")
    , VILLA(2, "VILLA")
    , LAND(3, "LAND")
    , STORE(4, "STORE");


    private final int value;
    private final String name;
    private static final LinkedHashMap<Integer, EstateType> valueMap = new LinkedHashMap<Integer, EstateType>();
    private static final LinkedHashMap<String, EstateType> nameMap = new LinkedHashMap<String, EstateType>();


    static {
        for (EstateType type : values()) {
            valueMap.put(type.value, type);
            nameMap.put(type.name, type);
        }
    }

    EstateType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static EstateType findByValue(int value) {
        return valueMap.get(value);
    }

    public static EstateType findByName(String name) {
        return nameMap.get(name);
    }
}
