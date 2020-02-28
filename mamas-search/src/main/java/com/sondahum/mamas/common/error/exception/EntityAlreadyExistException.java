package com.sondahum.mamas.common.error.exception;

import lombok.Getter;

@Getter
public class EntityAlreadyExistException extends RuntimeException {

    private String name;


    // todo 이미 있다는 에러니깐.. 중복으로 만들때 나는거겠지?
    public EntityAlreadyExistException(String name) {
        this.name = name;
    }


}
