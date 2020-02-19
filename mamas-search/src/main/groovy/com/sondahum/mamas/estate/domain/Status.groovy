package com.sondahum.mamas.estate.domain

enum Status {
    ONSALE(1, "ONSALE"),
    UNDERSALE(2,"UNDERSALE"),
    SOLD(3,"SOLD")

    private final int value
    private final String name
    private static final LinkedHashMap<Integer, Status> valueMap = [:]
    private static final LinkedHashMap<String, Status> nameMap = [:]

    static {
        for (Status status : values()) {
            valueMap.put(status.value, status)
            nameMap.put(status.name, status)
        }
    }

    Status(int value, String name) {
        this.value = value
        this.name = name
    }

    static Status findByValue(int value) {
        return valueMap[value]
    }

    static Status findByName(String name) {
        return nameMap[name]
    }


}