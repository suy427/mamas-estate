package com.sondahum.mamas.common.domain.product

import com.sondahum.mamas.common.domain.Entity
import com.sondahum.mamas.common.domain.common.Address
import com.sondahum.mamas.common.domain.type.ContractType
import com.sondahum.mamas.common.domain.type.EstateType

class Estate extends Entity{ //
    String name
    Address address
    EstateType productType
    ContractType contractType
}
