package com.sondahum.mamas.domain

import com.sondahum.mamas.model.BaseEntity
import org.springframework.format.annotation.DateTimeFormat

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "bid")
class Bid extends BaseEntity {

    @Column(name = "user_id") // column name in this table
    @OneToOne
    @JoinColumn(name = "user_id") // column name that reference in 'user' table
//    Long userId   // 이게 아니라
    User user       // 이거다!! 관계지향적으로다가 그 칼럼의 값만 가져오는게 아니라 진짜 객체지향적으로 바꿔준다!!

    String action

    @Column(name = "estate_id") // column name in this table
    @OneToOne
    @JoinColumn(name = "estate_id") // column name that reference in 'user' table
//    Long estateId // 이게 아니라
    Estate estate   // 이거다!!! 아~~ 진짜 관계지향 --> 객체지향으로 맵핑하는구나!!

    Long price
}
