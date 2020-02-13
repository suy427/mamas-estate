package com.sondahum.mamas.model

enum ContractType {
    RENT(1,"MAMA"),
    BARGAIN(2, "AGENT"),
    MONTHLY(3, "OTHER")


    private final int value
    private final String name
    private static final LinkedHashMap<Integer, ContractType> valueMap = [:]
    private static final LinkedHashMap<String, ContractType> nameMap = [:]

    static {
        for (ContractType contractType : values()) {
            valueMap.put(contractType.value, contractType)
            nameMap.put(contractType.name, contractType)
        }
    }

    ContractType(int value, String name) {
        this.value = value
        this.name = name
    }

    static ContractType findByValue(int value) {
        return valueMap[value]
    }

    static ContractType findByName(String name) {
        return nameMap[name]
    }

}