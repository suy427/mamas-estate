package com.sondahum.mamas.common.model.product

import com.sondahum.mamas.common.model.Address
import com.sondahum.mamas.common.model.type.ContractType
import com.sondahum.mamas.common.model.type.EstateType

class Estate { // 이 땅이 무슨 땅이고,
    String name
    Address address
    EstateType productType
    ContractType contractType
}
