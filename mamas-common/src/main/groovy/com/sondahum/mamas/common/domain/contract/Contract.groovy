package com.sondahum.mamas.common.domain.contract

import com.sondahum.mamas.common.domain.person.Agent
import com.sondahum.mamas.common.domain.person.Client
import com.sondahum.mamas.common.domain.product.Estate
import com.sondahum.mamas.common.domain.type.Status

// 모든 정보의 단위는 Contract단위...!
class Contract {
    String id
    Estate estate
    Client seller
    Client buyer
    Agent agent
    Date registeredDate
    Date modifiedDate
    Status status
}
