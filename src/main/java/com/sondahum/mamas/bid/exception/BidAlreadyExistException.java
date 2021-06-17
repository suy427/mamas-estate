package com.sondahum.mamas.bid.exception;


import com.sondahum.mamas.bid.adaptor.out.persistence.Bid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BidAlreadyExistException extends RuntimeException {

    private final Bid bid;
    private String message;
}
