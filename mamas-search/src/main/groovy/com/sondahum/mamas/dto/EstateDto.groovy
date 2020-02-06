package com.sondahum.mamas.dto

import com.sondahum.mamas.model.Address

import java.time.LocalDate

class EstateDto {
    String name
    LocalDate createdDate
    Address address
    String area
    UserDto owner
    String price
    String market_price
}
