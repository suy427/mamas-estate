package com.sondahum.mamas.model

enum EstateType {
    APT(1, "APT"),
    VILLA(2, "VILLA"),
    LAND(3, "LAND"),
    STORE(4, "STORE")

    private final int value
    private final String name
    private static final LinkedHashMap<Integer, EstateType> valueMap = [:]
    private static final LinkedHashMap<String, EstateType> nameMap = [:]

    static {
        for (EstateType estateType : values()) {
            valueMap.put(estateType.value, estateType)
            nameMap.put(estateType.name, estateType)
        }
    }

    EstateType(int value, String name) {
        this.value = value
        this.name = name
    }

    static EstateType findByValue(int value) {
        return valueMap[value]
    }

    static EstateType findByName(String name) {
        return nameMap[name]
    }
}
