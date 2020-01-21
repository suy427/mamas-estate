package com.sondahum.mamas.elasticsearch.dto

import com.sondahum.mamas.common.model.product.Estate

class EstateDto extends EsDto{

    private List<Estate> estateList

    @Override
    List<?> getItemList() {
        return estateList
    }
}
