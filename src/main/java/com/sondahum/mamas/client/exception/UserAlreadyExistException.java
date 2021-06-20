package com.sondahum.mamas.client.exception;


import com.sondahum.mamas.client.adaptor.out.persistence.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class UserAlreadyExistException extends RuntimeException {

    private String message;
    private final Client client;

    public UserAlreadyExistException(Client client) {
        this.client = client;
    }

}
