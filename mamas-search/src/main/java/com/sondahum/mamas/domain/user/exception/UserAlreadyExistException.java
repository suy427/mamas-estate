package com.sondahum.mamas.domain.user.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserAlreadyExistException extends RuntimeException {

    private String reason;

}
