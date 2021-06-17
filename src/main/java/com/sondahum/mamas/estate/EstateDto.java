package com.sondahum.mamas.estate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sondahum.mamas.Range;
import com.sondahum.mamas.estate.model.ContractType;
import com.sondahum.mamas.estate.adaptor.out.persistence.Estate;
import com.sondahum.mamas.estate.model.EstateType;
import com.sondahum.mamas.estate.model.EstateStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

public class EstateDto {

    @Getter
    @Builder
    public static class CreateReq {
        @NotEmpty(message = "등록할 부동산 이름을 입력해주세요.")
        private String name;
        @NotEmpty(message = "소유자 이름을 입력해주세요.")
        private String ownerName;
        @NotEmpty(message = "등록할 부동산 주소를 입력해주세요.")
        private Address address;
        private Double area;
        private EstateStatus status;
        private EstateType estateType;
        private ContractType contractType;
        private Range.Price ownerRequirePriceRange;
        private Range.Price marketPriceRange;

        public Estate toEntity() {
            return Estate.builder()
                    .name(name)
                    .address(address)
                    .area(area)
                    .marketPriceRange(marketPriceRange)
                    .contractType(contractType)
                    .estateType(estateType)
                    .status(status)
                    .ownerRequirePriceRange(ownerRequirePriceRange)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class UpdateReq {
        private String name;
        private Address address;
        private Double area;
        private EstateStatus status;
        private EstateType estateType;
        private ContractType contractType;
        private Range.Price ownerRequirePriceRange;
        private Range.Price marketPriceRange;
    }

    @Getter
    @Builder
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    public static class SearchReq {
        private String name;
        private String address;
        private Range.Area area;
        private EstateStatus status;
        private Range.Price ownerRequirePriceRange;
        private Range.Price marketPriceRange;
        private ContractType contractType;
        private EstateType estateType;
        private String owner;
    }

    @Getter
    public static class SimpleForm {
        private Long id;
        private String name;
        private String ownerName;
        private String address3; // most detailed address
        private EstateStatus status;
        private EstateType estateType;
        private ContractType contractType;

        public SimpleForm(Estate estate) {
            this.id = estate.getId();
            this.name = estate.getName();
            this.ownerName = estate.getOwner().getName();
            this.address3 = estate.getAddress().getAddress3();
            this.status = estate.getStatus();
            this.contractType = estate.getContractType();
            this.estateType = estate.getEstateType();
        }

    }


    @Getter
    public static class DetailForm {
        private Long id;
        private String name;
        private Address address;
        private String area;
        private String ownerName;
        private EstateStatus status;
        private EstateType estateType;
        private ContractType contractType;
        private Range.Price ownerRequirePriceRange;
        private Range.Price marketPriceRange;

        public DetailForm(Estate estate) {
            this.id = estate.getId();
            this.name = estate.getName();
            this.address = estate.getAddress();
            this.area = estate.getArea().toString();
            this.ownerName = estate.getOwner().getName();
            this.status = estate.getStatus();
            this.estateType = estate.getEstateType();
            this.contractType = estate.getContractType();
            this.ownerRequirePriceRange = estate.getOwnerRequirePriceRange();
            this.marketPriceRange = estate.getMarketPriceRange();
        }
    }
}
