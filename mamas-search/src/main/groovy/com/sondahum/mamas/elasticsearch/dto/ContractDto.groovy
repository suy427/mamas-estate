package com.sondahum.mamas.elasticsearch.dto

import com.sondahum.mamas.common.model.contract.Contract

class ContractDto extends EsDto{

    private List<Contract> contractList

    @Override
    List<?> getItemList() {
        return contractList
    }
}
