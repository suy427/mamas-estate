package com.sondahum.mamas.contract;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sondahum.mamas.Range;
import com.sondahum.mamas.contract.adaptor.out.persistence.Contract;
import com.sondahum.mamas.estate.model.ContractType;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

public class ContractDto {

    @Getter
    @Builder
    public static class CreateReq {
        @NotEmpty(message = "매도자 이름을 입력해주세요.")
        private String seller;
        @NotEmpty(message = "매수자 이름을 입력해주세요.")
        private String buyer;
        @NotEmpty(message = "계약한 부동산 이름을 입력해주세요.")
        private String estate;
        private ContractType contractType;
        private Long price;
        private Instant contractedDate;
        private Instant expireDate;

        public Contract toEntity() {
            return Contract.builder()
                    .price(price)
                    .contractedDate(contractedDate)
                    .expireDate(expireDate).build();
        }
    }

    @Getter
    public static class UpdateReq {
        private Long price;
        private ContractType contractType;
        private Instant expireDate;
        private Instant contractedDate;
    }

    @Getter
    @Builder
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    public static class SearchReq {
        private String estate;
        private String buyer;
        private String seller;
        private Range.Price contractedPrice;
        private Range.Date contractedDate;
    }


    @Getter
    public static class DetailForm {
        private Long id;
        private String seller;
        private String buyer;
        private String estate;
        private Long price;
        private Instant contractedDate;

        public DetailForm(Contract contract) {
            this.id = contract.getId();
            this.seller = contract.getSeller().getName();
            this.buyer = contract.getBuyer().getName();
            this.estate = contract.getEstate().getName();
            this.price = contract.getPrice();
            this.contractedDate = contract.getCreatedDate();
        }
    }
}
