package com.sondahum.mamas.domain

import com.sondahum.mamas.model.BaseEntity
import org.springframework.format.annotation.DateTimeFormat

import javax.persistence.AttributeOverride
import javax.persistence.AttributeOverrides
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "bid")\
@AttributeOverride(name = "id", column = @Column(name = "bid_id"))
class Bid extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user

    String action

    @Column(name = "estate_id") // column name in this table
    @OneToOne
    @JoinColumn(name = "estate_id") // column name that reference in 'user' table
    Estate estate   // 이거다!!! 아~~ 진짜 관계지향 --> 객체지향으로 맵핑하는구나!!

    String price
}
