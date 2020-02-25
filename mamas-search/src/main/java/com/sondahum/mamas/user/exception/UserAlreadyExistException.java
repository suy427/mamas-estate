package com.sondahum.mamas.user.exception;

import lombok.Getter;

@Getter
public class UserAlreadyExistException extends RuntimeException {

    private String property; // name, phone둘 다 될 수 있는데 이거 해결..
    private String field;


    public UserAlreadyExistException(String value) {
        this.property = value;
        if (value.matches("^[ㄱ-ㅎ가-힣]*$")) { // 한글(이름)
            this.field = "name";
        } else {                                       // 아님 번호
            this.field = "phone";
        }
    }


}
