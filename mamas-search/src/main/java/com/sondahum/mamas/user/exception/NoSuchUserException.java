package com.sondahum.mamas.user.exception;

import lombok.Getter;

@Getter
public class NoSuchUserException extends RuntimeException {

    private long id;

    public NoSuchUserException(long id) {
        this.id = id;
    }

}
