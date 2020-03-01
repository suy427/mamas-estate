package com.sondahum.mamas.dto;

import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.domain.contract.Contract;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.user.User;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

public class ContractDto {

    @Getter
    public static class CreateReq {
        @NotEmpty(message = "매도자 이름을 입력해주세요.")
        private String seller;
        @NotEmpty(message = "매수자 이름을 입력해주세요.")
        private String buyer;
        @NotEmpty(message = "계약한 부동산 이름을 입력해주세요.")
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
        @NotEmpty(message = "변경할 매도자 이름을 입력해주세요.")
        private String seller;
        @NotEmpty(message = "변경할 매수자 이름을 입력해주세요.")
        private String buyer;
        @NotEmpty(message = "변경할 부동산 이름을 입력해주세요.")
        private String estate;
        private Long price;
        private LocalDate contractedDate;
    }

    @Getter
    public static class SearchReq {
        private String estate;
        private String buyer;
        private String seller;
        private Range.Price contractedPrice;
        private Range.Date contractedDate;
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
