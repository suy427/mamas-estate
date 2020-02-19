package com.sondahum.mamas.bid.dto

import com.sondahum.mamas.bid.domain.Bid
import com.sondahum.mamas.estate.domain.Estate
import com.sondahum.mamas.user.domain.User
import com.sondahum.mamas.model.Price

import java.time.LocalDate

class BidDto {
    String userName
    Price price
    String estateName
    String action
    LocalDate createdDate

    static class CreateReq {}
    static class UpdateReq {}
    static class Response {}
    //remove 는 id가 바로 넘어가면 될듯!!

    Bid toEntity() {
        return new Bid(
                id: 0,
                user: new User(name: userName),
                action: action,
                estate: new Estate(name: estateName),
                minimumPrice: price.minimum,
                maximumPrice: price.maximum
        )
    }
}
// 이게 꼭 통째로 entity랑 매칭이 안되는구나...ㅠㅠ 그래서 용도별로 dto가 따로있는거였군...!!!
// 그래서 dto가 부지기수로 나온다는거였구나... 그치만 그래도 그렇게 하는게 나을까 modelmapper나 mapstruct를 쓰는게 나을까...?!!!