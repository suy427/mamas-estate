package com.sondahum.mamas.user.exception;

import com.sondahum.mamas.user.domain.Phone;
import com.sondahum.mamas.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserAlreadyExistException extends RuntimeException {

    private String name; // name, phone둘 다 될 수 있는데 이거 해결..
    private Phone phone;
    private String field;


    public UserAlreadyExistException(UserDto.CreateReq userDto) {
        this.name = userDto.getName();
        this.phone = userDto.getPhone();

        if (this.name == null && this.name.equals("")) {
            this.field += "[ name ] ";
        }
        if (this.phone == null) {
            this.field += " [ phone ]";
        }
    }


}
