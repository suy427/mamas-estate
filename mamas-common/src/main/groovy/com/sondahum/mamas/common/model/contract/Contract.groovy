package com.sondahum.mamas.common.model.contract

import com.sondahum.mamas.common.model.person.Agent
import com.sondahum.mamas.common.model.person.Client
import com.sondahum.mamas.common.model.product.Estate
import com.sondahum.mamas.common.model.type.Status

class Contract {
    Estate estate
    Client seller
    Client buyer
    Agent agent
    Date registeredDate
    Date modifiedDate
    Status status
}
