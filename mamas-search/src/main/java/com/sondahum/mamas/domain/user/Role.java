package com.sondahum.mamas.domain.user;

import java.util.LinkedHashMap;

public enum Role {
    MAMA(1, "MAMA")
    , AGENT(2, "AGENT")
    , OTHER(3, "OTHER");

    private final int value;
    private final String name;
    private static final LinkedHashMap<Integer, Role> valueMap = new LinkedHashMap<Integer, Role>();
    private static final LinkedHashMap<String, Role> nameMap = new LinkedHashMap<String, Role>();

    static {
        for (Role role : values()) {
            valueMap.put(role.value, role);
            nameMap.put(role.name, role);
        }
    }

    Role(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Role findByValue(int value) {
        return valueMap.get(value);
    }

    public static Role findByName(String name) {
        return nameMap.get(name);
    }
}
