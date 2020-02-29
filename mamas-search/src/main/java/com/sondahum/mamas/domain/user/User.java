package com.sondahum.mamas.domain.user;

import com.sondahum.mamas.domain.bid.Action;
import com.sondahum.mamas.domain.estate.model.Status;
import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.contract.Contract;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.user.model.Phone;
import com.sondahum.mamas.domain.user.model.Role;
import com.sondahum.mamas.dto.UserDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Builder
@Getter
@Setter
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany
    List<Contract> contractList = new ArrayList<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    @CreatedDate
    LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    @LastModifiedDate
    LocalDate modifiedDate;



    public List<Contract> getSoldList() {
        return contractList.stream()
                .filter(contract -> contract.getSeller().id.equals(this.id))
                .collect(Collectors.toList());

    }

    public List<Contract> getBoughtList() {
        return contractList.stream()
                .filter(contract -> contract.getBuyer().id.equals(this.id))
                .collect(Collectors.toList());
    }


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
        return Collections.max(contractList).getCreatedDate();
    }

    public void updateUserInfo(UserDto.UpdateReq userDto) {
        this.name = userDto.getName();
        this.phone = userDto.getPhone();
        this.role = Role.findByName(userDto.getRole());
    }


}
