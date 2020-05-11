package com.sondahum.mamas.domain.user;

import com.sondahum.mamas.common.model.BaseEntity;
import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.bid.model.BidStatus;
import com.sondahum.mamas.domain.contract.Contract;
import com.sondahum.mamas.domain.estate.Estate;

import com.sondahum.mamas.domain.user.model.Role;
import com.sondahum.mamas.dto.UserDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class User extends BaseEntity {


    @Column(name = "user_name")
    private String name;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    // todo 이게 없으면 list가 null로 초기화된다... new ArrayList<>()가 안되구나...
    // todo bid를 못만들어서 그런가...?
    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Bid> bidList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)
    private List<Estate> estateList = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Contract> contractList = new ArrayList<>();

    public void addContractHistory(Contract contract) {
        contractList.add(contract);
    }

    public void addBidHistory(Bid bid) {
        bidList.add(bid);
    }

    public void addEstate(Estate estate) {
        estateList.add(estate);
    }

    public User updateUserInfo(UserDto.UpdateReq userDto) {
        this.name = userDto.getName();
        this.phone = userDto.getPhone();
        this.role = userDto.getRole();

        return this;
    }


}
