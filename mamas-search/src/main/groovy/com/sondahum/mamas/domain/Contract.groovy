package com.sondahum.mamas.domain

import com.sondahum.mamas.model.BaseEntity
import org.springframework.format.annotation.DateTimeFormat

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "contract")
class Contract extends BaseEntity { // 거래중... 이라는 경우는 없겠구나.. 아닌가..ㅠ 이력으로 쓸려고 했는데..

    @Column(name = "order_sell_id")
    @JoinColumn(name = "id")
    Long seller

    @Column(name = "order_buy_id")
    @JoinColumn(name = "id")
    Long buyer

    @Enumerated(EnumType.STRING)
    Status status


    enum Status {
        ING, DONE
    }
}
