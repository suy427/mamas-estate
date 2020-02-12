package com.sondahum.mamas.domain

import com.sondahum.mamas.dto.UserDto
import com.sondahum.mamas.model.NamedEntity
import com.sondahum.mamas.model.Role
import groovy.transform.builder.Builder

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
@Builder
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@AttributeOverride(name = "name", column = @Column(name = "user_name"))
class User extends NamedEntity {

    @Column(name = "phone_number")
    String phone

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    Role role

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bid_id") // jpa라고 해서 object와 table을 동일한 형태로 만드는게 아니라 'mapping'해주는거다.
    List<Bid> bidList

    UserDto toDto() {
        return new UserDto(
                name: name,
                phone: phone,
                role: role,
                sellingEstateNumber: bidList.findAll{it.action == 'sell'}.size(),
                buyingEstateNumber: bidList.findAll{it.action == 'buy'}.size(),
                recentContractDate: bidList.last().createdDate
        )
    }
}
