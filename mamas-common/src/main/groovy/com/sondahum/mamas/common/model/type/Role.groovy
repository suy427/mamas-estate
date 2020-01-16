package com.sondahum.mamas.common.model.type

enum Role {
    MOM(1, '엄마'),
    ADMIN(2, '직원'),
    GUEST(3, '외부인'),
    CLIENT(4, '고객')

    private int value
    private String authority

    Role(int value, String authority) {
        this.value = value
        this.authority = authority
    }
}