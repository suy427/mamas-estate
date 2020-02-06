package com.sondahum.mamas.domain

import com.sondahum.mamas.model.NamedEntity

import javax.persistence.AttributeOverride
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
@Table(name = "user")
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@AttributeOverride(name = "name", column = @Column(name = "user_name"))
class User extends NamedEntity {

    @Column(name = "phone_number")
    String phone

    @Enumerated(EnumType.STRING)
    Role role

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JoinColumn(name = "bid_id") // jpa라고 해서 object와 table을 동일한 형태로 만드는게 아니라 'mapping'해주는거다.
    List<Bid> bidList

    enum Role {
        MAMA, AGENT, OTHER
    }
}