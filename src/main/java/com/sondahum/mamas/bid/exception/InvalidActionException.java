package com.sondahum.mamas.bid.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidActionException extends RuntimeException {

    private String message;
}
