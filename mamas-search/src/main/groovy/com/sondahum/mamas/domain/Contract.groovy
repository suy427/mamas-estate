package com.sondahum.mamas.domain

import com.sondahum.mamas.model.NamedEntity
import groovy.transform.builder.Builder

import javax.persistence.AttributeOverride
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "contract")
@Builder
@AttributeOverride(name = "id", column = @Column(name = "contract_id"))
@AttributeOverride(name = "name", column = @Column(name = "contract_name"))
@AttributeOverride(name = "createdDate", column = @Column(name = "contracted_date"))
class Contract extends NamedEntity {

    @Column(name = "seller", nullable = false)
    User seller

    @Column(name = "buyer", nullable = false)
    User buyer

    @Column(name = "price", nullable = false)
    String price
}
