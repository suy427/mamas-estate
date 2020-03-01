package com.sondahum.mamas.domain.bid.model;

import java.util.LinkedHashMap;

public enum Action {
    BUY(1, "BUY")
    , SELL(2, "SELL");


    private final int value;
    private final String name;
    private static final LinkedHashMap<Integer, Action> valueMap = new LinkedHashMap<Integer, Action>();
    private static final LinkedHashMap<String, Action> nameMap = new LinkedHashMap<String, Action>();

    static {
        for (Action action : values()) {
            valueMap.put(action.value, action);
            nameMap.put(action.name, action);
        }
    }

    Action(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Action findByValue(int value) {
        return valueMap.get(value);
    }

    public static Action findByName(String name) {
        return nameMap.get(name);
    }
}
