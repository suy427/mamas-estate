package com.sondahum.mamas.elasticsearch.dto

import com.sondahum.mamas.common.domain.person.User
import com.sondahum.mamas.common.domain.product.Estate

class ContractDto implements Serializable, EsDto{
    String contractId
    Estate estate
    User seller
    User buyer
    User agent
    String price
    String contractedDate
}
