package com.sondahum.mamas.contract.domain

import com.sondahum.mamas.contract.dto.ContractDto
import com.sondahum.mamas.estate.domain.Estate
import com.sondahum.mamas.model.Price
import com.sondahum.mamas.user.domain.User
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.format.annotation.DateTimeFormat
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "contract")
class Contract implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "seller", nullable = false)
    User seller

    @ManyToOne
    @JoinColumn(name = "buyer", nullable = false)
    User buyer

    @Column(name = "price", nullable = false)
    Long price

    @ManyToOne
    @JoinColumn(name = "estate_id", nullable = false)
    Estate estate

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    @CreatedDate
    LocalDate createdDate

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    @LastModifiedDate
    LocalDate modifiedDate

    void updateContractInfo(ContractDto.UpdateReq contractDto) {
        this.seller.name = contractDto.seller
        this.buyer.name = contractDto.buyer
        this.estate.name = contractDto.estate
        this.price = contractDto.price
        this.createdDate = contractDto.contractedDate
    }
}
