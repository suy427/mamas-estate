package com.sondahum.mamas.domain.user.exception;


import com.sondahum.mamas.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class UserAlreadyExistException extends RuntimeException {

    private String message;
    private final User user;

    public UserAlreadyExistException(User user) {
        this.user = user;
    }

}
