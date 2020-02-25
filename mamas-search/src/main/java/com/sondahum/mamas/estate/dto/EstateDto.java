package com.sondahum.mamas.estate.dto;

import com.sondahum.mamas.common.model.Address;
import com.sondahum.mamas.common.model.Price;
import com.sondahum.mamas.estate.domain.ContractType;
import com.sondahum.mamas.estate.domain.Estate;
import com.sondahum.mamas.estate.domain.EstateType;
import com.sondahum.mamas.estate.domain.Status;
import com.sondahum.mamas.user.domain.User;
import lombok.Getter;
import lombok.Setter;

public class EstateDto {


    public static class CreateReq {
        private String name;
        private Address address;
        private String area;
        private String ownerName;
        private Status status;
        private EstateType estateType;
        private ContractType contractType;
        private Price ownerRequirePriceRange;
        private Price marketPriceRange;

        public Estate toEntity() {
            User owner = User.builder().name(ownerName).build();

            return Estate.builder()
                    .name(name)
                    .address(address)
                    .area(area)
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
        private String name;
        private Address address;
        private String area;
        private String ownerName;
        private Status status;
        private EstateType estateType;
        private ContractType contractType;
        private Price ownerRequirePriceRange;
        private Price marketPriceRange;
    }

    public static class Response {
        private Long id;
        private String name;
        private Address address;
        private String area;
        private String ownerName;
        private Status status;
        private EstateType estateType;
        private ContractType contractType;
        private Price ownerRequirePriceRange;
        private Price marketPriceRange;

        public Response(Estate estate) {
            this.id = estate.getId();
            this.name = estate.getName();
            this.address = estate.getAddress();
            this.area = estate.getArea();
            this.ownerName = estate.getOwner().getName();
            this.status = estate.getStatus();
            this.estateType = estate.getEstateType();
            this.contractType = estate.getContractType();
            this.ownerRequirePriceRange = estate.getOwnerRequirePriceRange();
            this.marketPriceRange = estate.getMarketPriceRange();
        }
    }
}
