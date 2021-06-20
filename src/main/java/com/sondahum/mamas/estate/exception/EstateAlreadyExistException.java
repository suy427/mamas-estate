package com.sondahum.mamas.estate.exception;


import com.sondahum.mamas.estate.adaptor.out.persistence.Estate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EstateAlreadyExistException extends RuntimeException {

    private String message;
    private final Estate estate;

    public EstateAlreadyExistException(Estate estate) {
        this.estate = estate;
    }
}
