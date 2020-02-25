package com.sondahum.mamas.user.dto;

import com.sondahum.mamas.common.model.Role;
import com.sondahum.mamas.user.domain.Phone;
import com.sondahum.mamas.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

public class UserDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateReq {
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
        private String name;
        private Phone phone;
        private String role;
    }

    public static class Response {
        private Long id;
        private String name;
        private Phone phone;
        private Role role;
        private Integer owningEstateAmount;
        private Integer soldEstateAmount;
        private Integer buyingEstateAmount;
        private Integer boughtEstateAmount;
        private LocalDate recentContractDate;

        public Response(User user) {
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
