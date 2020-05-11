package com.sondahum.mamas.dto;

import com.sondahum.mamas.common.model.Range;
//import com.sondahum.mamas.domain.user.model.Phone;
import com.sondahum.mamas.domain.user.model.Role;
import com.sondahum.mamas.domain.user.User;
import lombok.*;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

public class UserDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateReq {
        private String name;
        private String phone;
        private Role role;

        public User toEntity() {
            return User.builder()
                    .name(name)
                    .phone(phone)
                    .role(role)
                    .build();
        }

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateReq {
        private String name;
        private String phone;
        private Role role;

        @Builder
        public UpdateReq(String name, String phone, Role role) {
            this.name = name;
            this.phone = phone;
            this.role = role;
        }
    }

    @Getter
    public static class SearchReq {
        private String name;
        private String phone;
        private Role role;
        private Range.Date bidDate;
        private Range.Date contractDate;
        private List<Sort.Order> sortOrders;
    }

    @Getter
    public static class SimpleForm { // list에 나올 기본정보
        private Long id;
        private String name;
        private String phone;
        private Role role;

        public SimpleForm(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.phone = user.getPhone();
            this.role = user.getRole();
        }
    }


    /*
        todo 얘를 불러올 때 이미 estate, bid, contract 다 조회하는데
        여기서 해당 버튼 누르면 또다시 그때마다 estate, bid, contract 다시 불러와야해..?
     */
    @Getter
    public static class DetailForm { // 실제 상세정보
        private Long id;
        private String name;
        private String phone;
        private Role role;
        private List<EstateDto.SimpleForm> owningEstateList; // 누르면 estate list pop up --> 파는거, 이미 계약된거 포함
        private List<BidDto.DetailForm> onTradingList;
        private List<ContractDto.DetailForm> contractHistoryList;


        public DetailForm(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.phone = user.getPhone();
            this.role = user.getRole();
            this.owningEstateList = user.getEstateList().stream().map(EstateDto.SimpleForm::new).collect(Collectors.toList());      // estate
            this.onTradingList = user.getBidList().stream().map(BidDto.DetailForm::new).collect(Collectors.toList());        // bid
            this.contractHistoryList = user.getContractList().stream().map(ContractDto.DetailForm::new).collect(Collectors.toList());   // contract
        }
    }

    enum SearchOrder {
        NAME,
    }
}
