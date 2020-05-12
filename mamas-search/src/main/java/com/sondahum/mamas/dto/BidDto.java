package com.sondahum.mamas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sondahum.mamas.common.config.SortDeserializer;
import com.sondahum.mamas.common.config.SortSerializer;
import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.domain.bid.model.BidStatus;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.estate.model.Address;
import com.sondahum.mamas.domain.user.User;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BidDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateReq {
        @NotEmpty(message = "등록할 고객의 이름을 입력해주세요.")
        private String userName;
        @NotEmpty(message = "등록할 매물의 이름을 입력해주세요.")
        private String estateName;
        private Address estateAddress;
        private Range.Price price;
        @NotNull(message = "매매 종류를 입력해주세요.")
        private Action action;

        public Bid toEntity() {
            return Bid.builder()
                    .priceRange(price)
                    .action(action)
                    .build();
        }

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateReq {
        private Range.Price price;
        private BidStatus status;
        @NotNull(message = "변경할 매매 종류를 입력해주세요..")
        private Action action;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SearchReq {
        private String user;
        private String estate;
        private Action action;
        private Range.Date date;
        private Range.Price price;

//        private Integer page;
//        private Integer size;
//        @JsonDeserialize(using = SortDeserializer.class)
//        @JsonSerialize(using = SortSerializer.class)
//        private List<Sort.Order> orders = new ArrayList<>();
    }

    @Getter //todo ResponseBody로 반환할 객체에 Getter/Setter가 없으면 binding이 안되는거같다... 이유를 알아보자
    public static class DetailForm {
        private Long id;
        private String user;
        private String estate;
        private Range.Price price;
        private String action;


        public DetailForm(Bid bid) {
            this.id = bid.getId();
            this.user = bid.getUser().getName();
            this.estate = bid.getEstate().getName();
            this.price = bid.getPriceRange();
            this.action = bid.getAction().name();
        }
    }
}
