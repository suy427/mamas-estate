package com.sondahum.mamas.estate.dto


import com.sondahum.mamas.model.Address
import com.sondahum.mamas.model.Price

class EstateDto {
    private String name
    Address address
    String area
    String ownerName
    String status
    String estateType // 이부분을 entity로 표현하려면 또 테이블이 있어야하는건가..?
    String contractType // 일단은 List<>에서 단건으로 가자
    Price desiredPrice
    Price marketPrice

    static class CreateReq {

    }
    static class UpdateReq {}
    static class Response {}
    //remove 는 id가 바로 넘어가면 될듯!!

}
