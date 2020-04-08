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
    String name;

    String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    Role role;

    @Builder.Default

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Bid> bidList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    List<Estate> estateList = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL)
    List<Contract> contractList = new ArrayList<>();


    public List<Bid> getTradingList() {
        return getBidList().stream()
                .filter(bid -> bid.getStatus() == BidStatus.ONGOING)
                .collect(Collectors.toList());
    }

    public LocalDateTime getRecentContractedDate() { // TODO 괜찮은걸로 보이긴한데 좀 찝찝하기도함..ㅎ
        return Collections.max(contractList).getCreatedDate();
    }

    public void updateUserInfo(UserDto.UpdateReq userDto) {
        this.name = userDto.getName();
        this.phone = userDto.getPhone();
        this.role = Role.findByName(userDto.getRole());
    }


}
