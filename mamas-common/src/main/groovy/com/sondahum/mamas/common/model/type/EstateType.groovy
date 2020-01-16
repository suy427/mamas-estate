package com.sondahum.mamas.common.model.type

enum EstateType {
    APT(1, '아파트'),
    LAND(2, '토지'),
    VILLA(3, '빌라')

    private int value
    private String type

    EstateType(int value, String type) {
        this.value = value
        this.type = type
    }

}