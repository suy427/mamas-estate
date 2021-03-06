package com.sondahum.mamas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.domain.contract.Contract;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.estate.model.ContractType;
import com.sondahum.mamas.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
        private LocalDateTime contractedDate;
        private LocalDateTime expireDate;

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
        private LocalDateTime expireDate;
        private LocalDateTime contractedDate;
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
        private LocalDateTime contractedDate;

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
