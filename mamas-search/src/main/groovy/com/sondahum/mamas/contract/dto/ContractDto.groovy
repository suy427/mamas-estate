package com.sondahum.mamas.contract.dto

import com.sondahum.mamas.contract.domain.Contract
import com.sondahum.mamas.estate.dto.EstateDto
import com.sondahum.mamas.user.dto.UserDto

import java.time.LocalDate

class ContractDto {


    static class CreateReq {
        String seller
        String buyer
        String estate
        Long price
        LocalDate contractedDate

        Contract toEntity() { // 얘는 들어갈때 유효성검사를 다 해줘야할듯.
            return new Contract(
                    seller: new UserDto.CreateReq(
                            name: seller
                    ).toEntity(),
                    buyer: new UserDto.CreateReq(
                            name: buyer
                    ).toEntity(),
                    estate: new EstateDto.CreateReq(
                            name: estate
                    ).toEntity(),
                    price: price,
                    createdDate: contractedDate
            )
        }
    }

    static class UpdateReq {
        String seller
        String buyer
        String estate
        Long price
        LocalDate contractedDate
    }

    static class Response {
        String seller
        String buyer
        String estate
        Long price
        LocalDate contractedDate

        Response(Contract contract) {
            this.seller = contract.seller.name
            this.buyer = contract.buyer.name
            this.estate = contract.estate.name
            this.price = contract.price
            this.contractedDate = contract.createdDate
        }
    }
    //remove 는 id가 바로 넘어가면 될듯!!

}
