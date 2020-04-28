package com.sondahum.mamas.domain.estate.model;

import java.util.LinkedHashMap;

public enum ContractType {
    RENT(1, "RENT")
    , BARGAIN(2, "SALE")
    , MONTHLY(3, "MONTH");


    private final int value;
    private final String name;
    private static final LinkedHashMap<Integer, ContractType> valueMap = new LinkedHashMap<Integer, ContractType>();
    private static final LinkedHashMap<String, ContractType> nameMap = new LinkedHashMap<String, ContractType>();


    static {
        for (ContractType type : values()) {
            valueMap.put(type.value, type);
            nameMap.put(type.name, type);
        }
    }

    ContractType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static ContractType findByValue(int value) {
        return valueMap.get(value);
    }

    public static ContractType findByName(String name) {
        return nameMap.get(name);
    }
}
