package com.sondahum.mamas.bid.domain

import com.sondahum.mamas.estate.domain.Estate
import com.sondahum.mamas.model.Price
import com.sondahum.mamas.user.domain.User
import lombok.Builder
import lombok.NoArgsConstructor
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.format.annotation.DateTimeFormat

import javax.persistence.AttributeOverride
import javax.persistence.AttributeOverrides
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table
import java.time.LocalDate

@Entity
@NoArgsConstructor
@Table(name = "bid")
class Bid implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_id")
    Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    User user

    @Column(name = "action", nullable = false)
    @Enumerated(EnumType.STRING)
    Action action

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "estate_id", nullable = false) // column name that reference in 'user' table
    Estate estate   // 이거다!!! 아~~ 진짜 관계지향 --> 객체지향으로 맵핑하는구나!!

    @Embedded
    @AttributeOverrides([
        @AttributeOverride(name="minimum", column=@Column(name="min_price")),
        @AttributeOverride(name="maximum", column=@Column(name="max_price"))
    ])
    Price priceRange

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    @CreatedDate
    LocalDate createdDate

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    @LastModifiedDate
    LocalDate modifiedDate
}
