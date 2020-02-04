package com.sondahum.mamas.domain

import com.sondahum.mamas.model.NamedEntity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.Table


@Entity
@Table(name = "estate")
class Estate extends NamedEntity {

    @Column(length = 100)
    String address

    String area // 면적

    @Column(name = "product_type")
    String productType

    @Column(name = "contract_type")
    String contractType

    @Column(name = "market_price", nullable = true)
    String marketPrice
}
