package com.sondahum.mamas.domain.user.exception;


import lombok.Getter;

import java.util.Arrays;


public class NotEnoughInfoException extends RuntimeException {

    private String msg = "At least one Information must be entered. ";
    private String[] elements;

    public NotEnoughInfoException(String... elements) {
        this.elements = elements;
    }

    public String getMsg() {
        return msg + "[ " + Arrays.toString(elements).replace(",", ", ") + " ]";
    }
}
