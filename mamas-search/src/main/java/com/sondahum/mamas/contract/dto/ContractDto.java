package com.sondahum.mamas.contract.dto;

import com.sondahum.mamas.contract.domain.Contract;
import com.sondahum.mamas.estate.domain.Estate;
import com.sondahum.mamas.estate.dto.EstateDto;
import com.sondahum.mamas.user.domain.User;
import com.sondahum.mamas.user.dto.UserDto;
import lombok.Getter;

import java.time.LocalDate;

public class ContractDto {


    public static class CreateReq {
        private String seller;
        private String buyer;
        private String estate;
        private Long price;
        private LocalDate contractedDate;

        public Contract toEntity() {// 얘는 들어갈때 유효성검사를 다 해줘야할듯.
            User seller = User.builder().name(this.seller).build();
            User buyer = User.builder().name(this.buyer).build();
            Estate estate = Estate.builder().name(this.estate).build();

            return Contract.builder()
                    .buyer(buyer)
                    .estate(estate)
                    .price(price)
                    .seller(seller)
                    .createdDate(contractedDate).build();
        }
    }

    @Getter
    public static class UpdateReq {
        private Long id;
        private String seller;
        private String buyer;
        private String estate;
        private Long price;
        private LocalDate contractedDate;
    }

    public static class SearchReq {

    }

    public static class DetailResponse {
        private Long id;
        private String seller;
        private String buyer;
        private String estate;
        private Long price;
        private LocalDate contractedDate;

        public DetailResponse(Contract contract) {
            this.id = contract.getId();
            this.seller = contract.getSeller().getName();
            this.buyer = contract.getBuyer().getName();
            this.estate = contract.getEstate().getName();
            this.price = contract.getPrice();
            this.contractedDate = contract.getCreatedDate();
        }
    }
}
