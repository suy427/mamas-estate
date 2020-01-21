package com.sondahum.mamas.common.domain.type

enum Status {
    PRESALE(1, '예정'),
    ONSALE(2,'거래가능'),
    ONGOING(3,'거래중'),
    CLOSED(4,'거래완료'),

    private int code
    private String represent

    Status(int code, String represent) {
        this.code = code
        this.represent = represent
    }
}