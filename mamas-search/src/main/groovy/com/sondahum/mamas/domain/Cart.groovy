package com.sondahum.mamas.domain

import com.sondahum.mamas.model.BaseEntity
import org.springframework.format.annotation.DateTimeFormat

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "cart")
class Cart extends BaseEntity { // 사는 중(ing...)인 상태

    @Column(name = "buyer_id")
    Long userId

    @Column(name = "estate_id")
    @ManyToMany
    @JoinColumn
    List<Estate> estate

    @Column(name = "desired_price")
    Long desiredPrice

}
