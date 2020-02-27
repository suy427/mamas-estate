package com.sondahum.mamas.bid.dto;

import com.sondahum.mamas.bid.domain.Action;
import com.sondahum.mamas.bid.domain.Bid;
import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.estate.domain.Estate;
import com.sondahum.mamas.user.domain.User;
import lombok.Getter;
import lombok.Setter;

public class BidDto {


    public static class CreateReq {
        private String user;
        private String estate;
        private Range.PriceRange price;
        private String action;

        public Bid toEntity() {
            User bidUser = User.builder().name(user).build();
            Estate bidEstate = Estate.builder().name(estate).build();

            return Bid.builder()
                    .priceRange(price)
                    .action(Action.findByName(action))
                    .user(bidUser)
                    .estate(bidEstate).build();
        }

    }

    @Getter
    @Setter
    public static class UpdateReq {
        private Long id;
        private String user;
        private String estate;
        private Range.PriceRange price;
        private String action;
    }

    public static class SearchReq {

    }

    public static class DetailResponse {
        private Long id;
        private String user;
        private String estate;
        private Range.PriceRange price;
        private String action;


        public DetailResponse(Bid bid) {
            this.id = bid.getId();
            this.user = bid.getUser().getName();
            this.estate = bid.getEstate().getName();
            this.price = bid.getPriceRange();
            this.action = bid.getAction().name();
        }
    }
}
