package com.sondahum.mamas.dto

import com.sondahum.mamas.domain.User
import com.sondahum.mamas.model.Address
import com.sondahum.mamas.model.Role

import java.time.LocalDate

class UserDto {

    String name
    String phone
    Role role
    Integer sellingEstateNumber
    Integer buyingEstateNumber
    LocalDate recentContractDate

    User toEntity() {
        return new User(
                name: name,
                role: role
        )
    }
}
