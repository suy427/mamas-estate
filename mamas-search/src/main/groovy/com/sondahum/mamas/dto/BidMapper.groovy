package com.sondahum.mamas.dto

import com.sondahum.mamas.domain.Bid
import org.mapstruct.Mapper

@Mapper
interface BidMapper {
    Bid toEntity(BidDto bidDto)
    BidDto toDto(Bid bid)
}
