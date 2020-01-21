package com.sondahum.mamas.common.domain.type

enum ContractType {
    BARGAIN(1, '매매'), // 매매
    RENT(2, '월세'), // 월세
    CHARTER(3, '전세') // 전세

    private int value
    private String represent

    ContractType(int value, String represent) {
        this.value = value
        this.represent = represent
    }

}