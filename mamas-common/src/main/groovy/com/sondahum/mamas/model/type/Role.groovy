package com.sondahum.mamas.model.type

enum Role {
    MOM(1),
    ADMIN(2),
    GUEST(3),
    CLIENT(4)

    private int value

    Role(int value) {
        this.value = value
    }
}