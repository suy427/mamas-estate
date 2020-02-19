package com.sondahum.mamas.contract.dto

import com.sondahum.mamas.contract.domain.Contract
import com.sondahum.mamas.user.dto.UserDto

import java.time.LocalDate

class ContractDto {
    UserDto seller
    UserDto buyer
    LocalDate createdDate
    String contractedDate

    static class CreateReq {}
    static class UpdateReq {}
    static class Response {}
    //remove 는 id가 바로 넘어가면 될듯!!

    Contract toEntity() {
        return new Contract(
                seller: new UserDto().toEntity()
        )
    }
}
