package com.sondahum.mamas.model

enum Role {
    MAMA(1,"MAMA"),
    AGENT(2, "AGENT"),
    OTHER(3, "OTHER")


    private final int value
    private final String name
    private static final LinkedHashMap<Integer, Role> valueMap = [:]
    private static final LinkedHashMap<String, Role> nameMap = [:]

    static {
        for (Role role : values()) {
            valueMap.put(role.value, role)
            nameMap.put(role.name, role)
        }
    }

    Role(int value, String name) {
        this.value = value
        this.name = name
    }

    static Role findByValue(int value) {
        return valueMap[value]
    }

    static Role findByName(String name) {
        return nameMap[name]
    }


}