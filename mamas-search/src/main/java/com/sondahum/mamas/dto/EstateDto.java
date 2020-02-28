package com.sondahum.mamas.dto;

import com.sondahum.mamas.common.model.Address;
import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.common.model.ContractType;
import com.sondahum.mamas.domain.Estate;
import com.sondahum.mamas.common.model.EstateType;
import com.sondahum.mamas.common.model.Status;
import com.sondahum.mamas.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

public class EstateDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateReq {
        @NotEmpty(message = "등록할 부동산 이름을 입력해주세요.")
        private String name;
        private Address address;
        private String area;
        private String ownerName;
        private Status status;
        private EstateType estateType;
        private ContractType contractType;
        private Range.PriceRange ownerRequirePriceRange;
        private Range.PriceRange marketPriceRange;

        public Estate toEntity() {
            User owner = User.builder().name(ownerName).build();

            return Estate.builder()
                    .name(name)
                    .address(address)
                    .area(Double.parseDouble(area))
                    .marketPriceRange(marketPriceRange)
                    .contractType(contractType)
                    .estateType(estateType)
                    .status(status)
                    .ownerRequirePriceRange(ownerRequirePriceRange)
                    .owner(owner).build();
        }
    }

    @Getter
    @Setter
    public static class UpdateReq {
        private Long id;
        @NotEmpty(message = "변경할 부동산 이름을 입력해주세요.")
        private String name;
        private Address address;
        private String area;
        private String ownerName;
        private Status status;
        private EstateType estateType;
        private ContractType contractType;
        private Range.PriceRange ownerRequirePriceRange;
        private Range.PriceRange marketPriceRange;
    }

    @Getter
    public static class SearchReq {
        private String name;
        private String address;
        private Range.AreaRange area;
        private Status status;
        private Range.PriceRange ownerRequirePriceRange;
        private Range.PriceRange marketPriceRange;
        private String owner;
    }

    public static class SearchResponse {
        private Long id;
        private String name;
        private String address3; // most detailed address
        private Status status;

        public SearchResponse(Estate estate) {
            this.id = estate.getId();
            this.name = estate.getName();
            this.address3 = estate.getAddress().getAddress3();
            this.status = estate.getStatus();
        }

    }

    public static class DetailResponse {
        private Long id;
        private String name;
        private Address address;
        private String area;
        private String ownerName;
        private Status status;
        private EstateType estateType;
        private ContractType contractType;
        private Range.PriceRange ownerRequirePriceRange;
        private Range.PriceRange marketPriceRange;

        public DetailResponse(Estate estate) {
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
