package com.sondahum.mamas.domain.bid.exception;


import com.sondahum.mamas.domain.bid.Bid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BidAlreadyExistException extends RuntimeException {

    private final Bid bid;
    private String message;
}
