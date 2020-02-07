package com.sondahum.mamas.dto

import com.sondahum.mamas.domain.Contract
import com.sondahum.mamas.domain.User

import java.time.LocalDate

class ContractDto {
    UserDto seller
    UserDto buyer
    LocalDate createdDate
    String contractedDate

    Contract toEntity() {
        return new Contract(
                seller: new UserDto().toEntity()
        )
    }
}
