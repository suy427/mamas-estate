package com.sondahum.mamas.elasticsearch.dto

import com.sondahum.mamas.common.domain.product.Estate



class EstateDto implements Serializable, EsDto{
    String estateId
    Estate estate
}
