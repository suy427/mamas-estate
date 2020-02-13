package com.sondahum.mamas.domain

import com.sondahum.mamas.model.ContractType
import com.sondahum.mamas.model.EstateType
import com.sondahum.mamas.model.NamedEntity
import groovy.transform.builder.Builder

import javax.persistence.AttributeOverride
import javax.persistence.AttributeOverrides
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table


@Entity
@Table(name = "estate")
@AttributeOverride(name = "id", column = @Column(name = "estate_id"))
@AttributeOverride(name = "name", column = @Column(name = "estate_name"))
class Estate extends NamedEntity {

    @Column(name = "address1")
    String address1

    @Column(name = "address2")
    String address2

    @Column(name = "address3")
    String address3

    @Column(name = "area")
    String area // 면적

    @Enumerated(EnumType.STRING)
    @Column(name = "contract_type")
    ContractType contractType

    @Enumerated(EnumType.STRING)
    @Column(name = "estate_type")
    EstateType estateType

    @Column(name = "market_min_price", nullable = true)
    String marketMinimumPrice

    @Column(name = "market_max_price", nullable = true)
    String marketMaximumPrice

    @Column(name = "owner_min_price")
    String ownerMinimumPrice

    @Column(name = "owner_max_price")
    String ownerMaximumPrice

    @OneToOne
    @JoinColumn(name = "seller") // column name that reference in 'user' table
    User sellerId
}
