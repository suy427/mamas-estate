package com.sondahum.mamas.model

enum Role {
    MAMA(1,"MAMA"),
    AGENT(2, "AGENT"),
    OTHER(3, "OTHER")


    private final int value
    private final String name

    Role(int value, String name) {
        this.value = value
        this.name = name
    }
}