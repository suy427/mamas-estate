package com.sondahum.mamas.common.error.exception;

import lombok.Getter;

@Getter
public class NoSuchEntityException extends RuntimeException {

    private long id;

    public NoSuchEntityException(long id) {
        this.id = id;
    }

}
