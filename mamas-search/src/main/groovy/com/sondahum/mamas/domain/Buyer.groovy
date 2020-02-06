package com.sondahum.mamas.domain

import com.sondahum.mamas.model.NamedEntity

import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "buyer")
class Buyer extends NamedEntity { // 살려고 하는사람

    @JoinColumn(name = "user_id")
    Long userId

    @OneToMany
    @JoinColumn(name = "buy_id")
    Cart orderBuy

    //date 있음.
}
