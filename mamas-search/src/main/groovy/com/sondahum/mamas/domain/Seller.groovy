package com.sondahum.mamas.domain

import com.sondahum.mamas.model.NamedEntity


import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "seller")
class Seller extends NamedEntity {

    @JoinColumn(name = "user_id")
    Long userId

    @JoinColumn(name = "estate_id")
    Long estateId

    //date 있음.
}
