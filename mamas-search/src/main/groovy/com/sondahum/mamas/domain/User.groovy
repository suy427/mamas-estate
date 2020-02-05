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
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
@Table(name = "user")
class User extends NamedEntity {

    @Column(name = "phone_number")
    String phone

//    이게 BaseEntity에 있는 날짜로 대체가 될까..?
//    @Column(name = "registered_date")
//    @Temporal(TemporalType.TIMESTAMP)
//    Date registeredDate

    @Enumerated(EnumType.STRING)
    Role role

    enum Role {
        MAMA, AGENT, OTHER
    }
}
