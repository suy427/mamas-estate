package com.sondahum.mamas.domain

import com.sondahum.mamas.model.NamedEntity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table


@Entity
@Table(name = "estate")
class Estate extends NamedEntity {

    String address1
    String address2
    String address3

    String area // 면적

    @Column(name = "contract_type")
    String contractType

    @Column(name = "estate_type")
    String estateType

    @Column(name = "market_price", nullable = true)
    String marketPrice

    @Column(name = "seller")
    @OneToOne
    @JoinColumn(name = "user_id") // column name that reference in 'user' table
    User sellerId
}
