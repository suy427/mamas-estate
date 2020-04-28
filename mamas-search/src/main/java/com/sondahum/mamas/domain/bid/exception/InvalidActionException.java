package com.sondahum.mamas.domain.bid.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class InvalidActionException extends RuntimeException {

    private String message;
}
