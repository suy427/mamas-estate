package com.sondahum.mamas.bid.dto

import com.sondahum.mamas.bid.domain.Action
import com.sondahum.mamas.bid.domain.Bid
import com.sondahum.mamas.estate.dto.EstateDto
import com.sondahum.mamas.model.Price
import com.sondahum.mamas.user.dto.UserDto

class BidDto {

    static class CreateReq {
        String user
        String estate
        Price price
        String action

        Bid toEntity() {
            return new Bid(
                    user: new UserDto.CreateReq(
                            name: user,
                    ).toEntity(),
                    action: Action.findByName(action),
                    estate: new EstateDto.CreateReq(
                            name: estate
                    ).toEntity(),
                    priceRange: price
            )
        }
    }
    //remove 는 id가 바로 넘어가면 될듯!!

    static class UpdateReq {
        String user
        String estate
        Price price
        String action
    }

    static class Response {
        String user
        String estate
        Price price
        String action

        Response(Bid bid) {
            this.user = bid.user.name
            this.estate = bid.estate.name
            this.price = bid.priceRange
            this.action = bid.action.name()
        }
    }
}

import java.time.LocalDate
// 이게 꼭 통째로 entity랑 매칭이 안되는구나...ㅠㅠ 그래서 용도별로 dto가 따로있는거였군...!!!
// 그래서 dto가 부지기수로 나온다는거였구나... 그치만 그래도 그렇게 하는게 나을까 modelmapper나 mapstruct를 쓰는게 나을까...?!!!