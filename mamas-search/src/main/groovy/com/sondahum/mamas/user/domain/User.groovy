package com.sondahum.mamas.user.domain

import com.sondahum.mamas.bid.domain.Action
import com.sondahum.mamas.bid.domain.Bid
import com.sondahum.mamas.contract.domain.Contract
import com.sondahum.mamas.estate.domain.Estate
import com.sondahum.mamas.estate.domain.Status
import com.sondahum.mamas.common.model.Role
import com.sondahum.mamas.user.dto.UserDto
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.format.annotation.DateTimeFormat

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "user")
class User implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Long id;

    @Column(name = "user_name")
    String name;

    @Column(name = "phone_number")
    String phone

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    Role role

    @OneToMany(mappedBy = "user")
    List<Bid> bidList = []

    @OneToMany(mappedBy = "owner")
    List<Estate> estateList = []

    @OneToMany(mappedBy = "seller")
    List<Contract> soldList = []

    @OneToMany(mappedBy = "buyer")
    List<Contract> boughtList = []

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    @CreatedDate
    LocalDate createdDate

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    @LastModifiedDate
    LocalDate modifiedDate

    List<Estate> getSellingList() {
        return estateList.findAll{it.status != Status.SOLD}
    }

    List<Bid> getBuyingList() {
        return bidList.findAll { (it.action == Action.BUY) && (it.estate.status == Status.ONSALE)}
    }

    LocalDate getRecentContractedDate() {
        LocalDate result

        LocalDate lastSold = soldList.sort({
            Contract e1, Contract e2 -> (e1.createdDate <=> e2.createdDate)
        }).last().createdDate

        LocalDate lastBought = boughtList.sort({
            Contract e1, Contract e2 -> (e1.createdDate <=> e2.createdDate)
        }).last().createdDate

        if (lastBought <=> lastSold) {
            result = lastSold
        } else {
            result = lastBought
        }

        return result
    }

    void updateUserInfo(UserDto.UpdateReq userDto) {
        this.name = userDto.name
        this.phone = userDto.phone
        this.role = Role.findByName(userDto.role)
    }


}
