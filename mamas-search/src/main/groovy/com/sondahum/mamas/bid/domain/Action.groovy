package com.sondahum.mamas.bid.domain

enum Action {
    BUY(1,"buy"),
    SELL(2,"sell")

    private final int value
    private final String name
    private static final LinkedHashMap<Integer, Action> valueMap = [:]
    private static final LinkedHashMap<String, Action> nameMap = [:]

    static {
        for (Action action : values()) {
            valueMap.put(action.value, action)
            nameMap.put(action.name, action)
        }
    }

    Action(int value, String name) {
        this.value = value
        this.name = name
    }

    static Action findByValue(int value) {
        return valueMap[value]
    }

    static Action findByName(String name) {
        return nameMap[name]
    }

}