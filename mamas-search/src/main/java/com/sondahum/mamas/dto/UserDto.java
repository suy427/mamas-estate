package com.sondahum.mamas.dto;

import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.domain.user.Phone;
import com.sondahum.mamas.domain.user.Role;
import com.sondahum.mamas.domain.user.User;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

public class UserDto {

    @Getter
    public static class CreateReq {
        @NotEmpty(message = "등록할 고객의 이름을 입력해주세요")
        private String name;
        private Phone phone;
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
        @NotEmpty(message = "변경할 고객의 이름을 입력해주세요")
        private String name;
        private Phone phone;
        private String role;
    }

    @Getter
    public static class SearchReq {
        private String name;
        private Phone phone;
        private Role role;
        private Range<LocalDate> bidDate;
        private Range<LocalDate> contractDate;
    }

    public static class SearchResponse {
        private Long id;
        private String name;
        private Phone phone;
        private Role role;

        public SearchResponse(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.phone = user.getPhone();
            this.role = user.getRole();
        }
    }

    public static class DetailResponse {
        private Long id;
        private String name;
        private Phone phone;
        private Role role;
        private Integer owningEstateAmount;
        private Integer soldEstateAmount;
        private Integer buyingEstateAmount;
        private Integer boughtEstateAmount;
        private LocalDate recentContractDate;

        public DetailResponse(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.phone = user.getPhone();
            this.role = user.getRole();
            this.owningEstateAmount = user.getSellingList().size();// 현재 파는거
            this.soldEstateAmount = user.getSoldList().size();
            this.buyingEstateAmount = user.getBuyingList().size();
            this.boughtEstateAmount = user.getBoughtList().size();
            this.recentContractDate = user.getRecentContractedDate();

        }
    }
}
