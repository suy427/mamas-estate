package com.sondahum.mamas.domain.user;

import com.sondahum.mamas.common.model.BaseEntity;
import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.bid.model.BidStatus;
import com.sondahum.mamas.domain.contract.Contract;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.user.model.Phone;
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



//    public List<Contract> getSoldList() { // 이런것도 query로 해결하는게 나을 수 있다.
//        return contractList.stream()
//                .filter(contract -> contract.getSeller().id.equals(this.id))
//                .collect(Collectors.toList());
//
//    }

//    public List<Contract> getBoughtList() {
//        return contractList.stream()
//                .filter(contract -> contract.getBuyer().id.equals(this.id))
//                .collect(Collectors.toList());
//    }


//    public List<Estate> getSellingList() {
//        return estateList.stream()
//                .filter(estate -> estate.getStatus() != Status.SOLD)
//                .collect(Collectors.toList());
//    }

//    public List<Bid> getBuyingList() {
//        return bidList.stream()
//                .filter(
//                        bid -> bid.getAction() == Action.BUY
//                                && bid.getEstate().getStatus() == Status.ONSALE
//                )
//                .collect(Collectors.toList());
//    }

    public List<Bid> getTradingList() {
        return getBidList().stream()
                .filter(bid -> bid.getStatus() == BidStatus.ONGOING)
                .collect(Collectors.toList());
    }

    public LocalDateTime getRecentBidDate() {
        return Collections.max(bidList).getCreatedDate();
    }

    public LocalDateTime getRecentContractedDate() { // TODO 쌉 하드코딩...
        return Collections.max(contractList).getCreatedDate();
    }

    public void updateUserInfo(UserDto.UpdateReq userDto) {
        this.name = userDto.getName();
        this.phone = userDto.getPhone();
        this.role = Role.findByName(userDto.getRole());
    }


}
