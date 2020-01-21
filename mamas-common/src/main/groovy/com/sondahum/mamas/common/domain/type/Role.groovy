package com.sondahum.mamas.common.domain.type

enum Role {
    MOM(1, '엄마'),
    AGENT(2, '직원'),
    GUEST(3, '외부인'),
    CLIENT(4, '고객')

    private int value
    private String represent

    Role(int value, String represent) {
        this.value = value
        this.represent = represent
    }
}