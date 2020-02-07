package com.sondahum.mamas.dto

import com.sondahum.mamas.model.Address
import com.sondahum.mamas.model.Price

import java.time.LocalDate

class EstateDto {
    String name
    Address address
    String area
    String owner
    List<String> estateType
    List<String> contractType
    Price desiredPrice
    Price marketPrice
}
