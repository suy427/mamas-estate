package com.sondahum.mamas.dto

import java.time.LocalDate

class ContractDto {
    UserDto seller
    UserDto buyer
    LocalDate createdDate
    String contractedDate
}
