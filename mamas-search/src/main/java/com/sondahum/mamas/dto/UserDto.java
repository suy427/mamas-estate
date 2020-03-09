package com.sondahum.mamas.dto;

import com.sondahum.mamas.common.model.Range;
//import com.sondahum.mamas.domain.user.model.Phone;
import com.sondahum.mamas.domain.user.model.Role;
import com.sondahum.mamas.domain.user.User;
import lombok.Getter;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class UserDto {

    @Getter
    public static class CreateReq {
        private String name;
        private String phone;
        private String role;

        public User toEntity() {
            return User.builder()
                    .name(name)
                    .phone(phone)
                    .role(Role.findByName(role))
                    .build();
        }

    }

    @Getter
    public static class UpdateReq {
        private Long id;
        private String name;
        private String phone;
        private String role;
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

    public static class SearchResponse { // list에 나올 기본정보
        private Long id;
        private String name;
        private String phone;
        private Role role;

        public SearchResponse(User user) {
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
    public static class DetailResponse { // 실제 상세정보
        private Long id;
        private String name;
        private String phone;
        private Role role;
        private Integer owningEstateAmount; // 누르면 estate list pop up --> 파는거, 이미 계약된거 포함
//      private Integer soldEstateAmount;   // 위에께 있기 때문에 이건 필요 없다.
//      private Integer boughtEstateAmount; // 따져보면 contract list를 불러오면 이걸 따로 볼 필요없음.
        private Integer onTradingAmount;
        private LocalDateTime recentContractDate;
//      private Integer buyingEstateAmount; // 누르면 bid list pop up --> 호가 걸어놓은거

        public DetailResponse(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.phone = user.getPhone();
            this.role = user.getRole();
            this.owningEstateAmount = user.getEstateList().size();      // estate
            this.onTradingAmount = user.getTradingList().size();        // bid
            this.recentContractDate = user.getRecentContractedDate();   // contract


//            this.owningEstateAmount = user.getSellingList().size();// 현재 파는거
//            this.soldEstateAmount = user.getSoldList().size();
//            this.buyingEstateAmount = user.getBuyingList().size();
//            this.boughtEstateAmount = user.getBoughtList().size();

        }
    }

    enum SearchOrder {
        NAME,
    }
}
