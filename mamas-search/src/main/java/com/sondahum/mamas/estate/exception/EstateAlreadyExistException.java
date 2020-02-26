package com.sondahum.mamas.estate.exception;

import com.sondahum.mamas.estate.dto.EstateDto;

public class EstateAlreadyExistException extends RuntimeException {


    public EstateAlreadyExistException(EstateDto.CreateReq estateDto) {
    }
}
