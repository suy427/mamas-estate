package com.sondahum.mamas.estate.dto

import com.sondahum.mamas.estate.domain.ContractType
import com.sondahum.mamas.estate.domain.Estate
import com.sondahum.mamas.estate.domain.EstateType
import com.sondahum.mamas.estate.domain.Status
import com.sondahum.mamas.model.Address
import com.sondahum.mamas.model.Price
import com.sondahum.mamas.user.domain.User

class EstateDto {
    String name
    Address address
    String area
    String ownerName
    String status
    String estateType // 이부분을 entity로 표현하려면 또 테이블이 있어야하는건가..?
    String contractType // 일단은 List<>에서 단건으로 가자
    Price desiredPrice
    Price marketPrice

    static class CreateReq {
        String name
        Address address
        String area // TODO 평수는 string인가 숫자인가
        String ownerName
        Status status
        EstateType estateType // 이부분을 entity로 표현하려면 또 테이블이 있어야하는건가..?
        ContractType contractType // 일단은 List<>에서 단건으로 가자
        Price ownerRequirePriceRange
        Price marketPriceRange

        Estate toEntity() {
            return new Estate(
                    name: name,
                    address: address,
                    area: area,
                    marketPriceRange: marketPriceRange,
                    ownerRequirePriceRange: ownerRequirePriceRange
                    estateType: estateType,
                    contractType: contractType,
                    status: status,
                    owner: new User(name: ownerName)
                    //owner: 일단 오너없이 return하고 service단이나 이런데서 쓸때, userRepo써서 넣는건 어떨까..?
            )
        }
    }

    static class UpdateReq {
        String name
        Address address
        String area
        String ownerName
        Status status
        EstateType estateType // 이부분을 entity로 표현하려면 또 테이블이 있어야하는건가..?
        ContractType contractType // 일단은 List<>에서 단건으로 가자
        Price ownerRequirePriceRange
        Price marketPriceRange
    }


    static class Response {
        String name
        Address address
        String area
        String ownerName
        Status status
        EstateType estateType // 이부분을 entity로 표현하려면 또 테이블이 있어야하는건가..?
        ContractType contractType // 일단은 List<>에서 단건으로 가자
        Price ownerRequirePriceRange
        Price marketPriceRange

        Response(Estate estate) {
            this.name = estate.name
            this.address = estate.address
            this.area = estate.area
            this.ownerName = estate.owner.name
            this.status = estate.status
            this.estateType = estate.estateType
            this.contractType = estate.contractType
            this.ownerRequirePriceRange = estate.ownerRequirePriceRange
            this.marketPriceRange = estate.marketPriceRange
        }
    }
    
    //remove 는 id가 바로 넘어가면 될듯!!

}
