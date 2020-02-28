package com.sondahum.mamas.domain.user;

import com.sondahum.mamas.domain.bid.Action;
import com.sondahum.mamas.domain.estate.Status;
import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.contract.Contract;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.dto.UserDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Builder
@Getter
@Setter
@Table(name = "user")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Long id;

    @Column(name = "user_name")
    String name;

    @Embedded
    Phone phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    Role role;

    @OneToMany(mappedBy = "user")
    List<Bid> bidList = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    List<Estate> estateList = new ArrayList<>();

    @OneToMany(mappedBy = "seller")
    List<Contract> soldList = new ArrayList<>();

    @OneToMany(mappedBy = "buyer")
    List<Contract> boughtList = new ArrayList<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    @CreatedDate
    LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    @LastModifiedDate
    LocalDate modifiedDate;


    public List<Estate> getSellingList() {
        return estateList.stream()
                .filter(estate -> estate.getStatus() != Status.SOLD)
                .collect(Collectors.toList());
    }

    public List<Bid> getBuyingList() {
        return bidList.stream()
                .filter(
                        bid -> bid.getAction() == Action.BUY
                                && bid.getEstate().getStatus() == Status.ONSALE
                )
                .collect(Collectors.toList());
    }

    public LocalDate getRecentBidDate() {
        Optional<Bid> optionalBid = bidList.stream()
                .max(Bid::compareTo);

        return optionalBid.map(Bid::getCreatedDate).orElse(null);
    }

    public LocalDate getRecentContractedDate() { // TODO 쌉 하드코딩...
        Contract recentSold = soldList.stream()
                .max(Contract::compareTo).orElse(null);

        Contract recentBought = boughtList.stream()
                .max(Contract::compareTo).orElse(null);

        if (recentBought == null && recentSold == null) {
            return null;
        } else if (recentBought == null ) {
            return recentSold.getCreatedDate();
        } else if (recentSold == null) {
            return recentBought.getCreatedDate();
        } else {
            if (recentBought.compareTo(recentSold) > 0) {
                return recentBought.getCreatedDate();
            } else {
                return recentSold.getCreatedDate();
            }
        }
    }

    public void updateUserInfo(UserDto.UpdateReq userDto) {
        this.name = userDto.getName();
        this.phone = userDto.getPhone();
        this.role = Role.findByName(userDto.getRole());
    }


}
