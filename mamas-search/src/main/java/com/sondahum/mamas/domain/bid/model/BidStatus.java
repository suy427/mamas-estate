package com.sondahum.mamas.domain.bid.model;

import java.util.LinkedHashMap;



public enum BidStatus {

    WIN(1, "WIN")
    , PROBABLE(2, "PROBABLE")
    , ONGOING(3, "ONGOING");

    private final int value;
    private final String name;
    private static final LinkedHashMap<Integer, BidStatus> valueMap = new LinkedHashMap<Integer, BidStatus>();
    private static final LinkedHashMap<String, BidStatus> nameMap = new LinkedHashMap<String, BidStatus>();

    static {
        for (BidStatus status : values()) {
            valueMap.put(status.value, status);
            nameMap.put(status.name, status);
        }
    }

    BidStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static BidStatus findByValue(int value) {
        return valueMap.get(value);
    }

    public static BidStatus findByName(String name) {
        return nameMap.get(name);
    }

}
