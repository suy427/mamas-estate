package com.sondahum.mamas.dto

import com.sondahum.mamas.model.Address
import com.sondahum.mamas.model.Role

import java.time.LocalDate

class UserDto {
    String name
    String phone
    Address address
    Role role
    List<BidDto> bidDtoListList
    LocalDate createdDate

    void toEntity() {

    }
}
