package com.sondahum.mamas.estate.domain

import com.sondahum.mamas.bid.domain.Bid
import com.sondahum.mamas.model.Address
import com.sondahum.mamas.model.Price
import com.sondahum.mamas.user.domain.User
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
import javax.persistence.OneToMany
import javax.persistence.Table
import java.time.LocalDate


@Entity
@Table(name = "estate")
class Estate implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estate_id")
    Long id;

    @Column(name = "estate_name")
    String name;

    @Embedded
    Address address

    @Column(name = "area")
    String area // 면적

    @Enumerated(EnumType.STRING)
    @Column(name = "contract_type")
    ContractType contractType

    @Enumerated(EnumType.STRING)
    @Column(name = "estate_type")
    EstateType estateType

    @Embedded
    @AttributeOverrides([
        @AttributeOverride(name="minimum", column=@Column(name="market_min_price")),
        @AttributeOverride(name="maximum", column=@Column(name="market_max_price"))
    ])
    Price marketPriceRange

    @Embedded
    @AttributeOverrides([
        @AttributeOverride(name="minimum", column=@Column(name="owner_min_price")),
        @AttributeOverride(name="maximum", column=@Column(name="owner_max_price"))
    ])
    Price ownerRequirePriceRange

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    Status status

    @OneToMany
    @JoinColumn(name = "bid_id")
    List<Bid> bidList

    @ManyToOne
    @JoinColumn(name = "owner") // column name that reference in 'user' table
    User owner

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    @CreatedDate
    LocalDate createdDate

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    @LastModifiedDate
    LocalDate modifiedDate
}
