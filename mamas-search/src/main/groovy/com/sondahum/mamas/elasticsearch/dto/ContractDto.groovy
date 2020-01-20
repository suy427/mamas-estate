package com.sondahum.mamas.elasticsearch.dto

import com.sondahum.mamas.common.model.contract.Contract

class ContractDto implements EsDto{

    private Double totalCount
    private List<Contract> contractList
}
