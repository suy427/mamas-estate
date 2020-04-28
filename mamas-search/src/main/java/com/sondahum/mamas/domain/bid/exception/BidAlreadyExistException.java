package com.sondahum.mamas.domain.bid.exception;


import com.sondahum.mamas.domain.bid.Bid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BidAlreadyExistException extends RuntimeException {

    private String message;
    private final Bid bid;
}
