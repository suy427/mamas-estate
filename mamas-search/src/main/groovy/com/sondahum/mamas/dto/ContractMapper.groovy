package com.sondahum.mamas.dto

import com.sondahum.mamas.domain.Contract
import org.mapstruct.Mapper


@Mapper
interface ContractMapper {
    Contract toEntity(ContractDto contractDto)
    ContractDto toDto(Contract contract)
}