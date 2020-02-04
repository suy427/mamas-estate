package com.sondahum.mamas.domain

import com.sondahum.mamas.model.BaseEntity
import org.springframework.format.annotation.DateTimeFormat

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "order_sell")
class OrderSell extends BaseEntity {

    @Column(name = "user_id")
    Long userId

    @Column(name = "estate_id")
    Long estateId

    @Column(name = "desired_price")
    Long desiredPrice


}
