package com.sondahum.mamas.domain

import com.sondahum.mamas.model.NamedEntity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "user")
class User extends NamedEntity {

    @Column(length = 16, nullable = true)
    String phone

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    List<Cart> buy

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    List<OrderSell> sell

    @Enumerated(EnumType.STRING)
    Role role

    enum Role {
        MAMA, AGENT, OTHER
    }
}
