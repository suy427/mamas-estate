package com.sondahum.mamas.dto;

import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class BidDto {

    @Getter
    public static class CreateReq {
        @NotEmpty(message = "등록할 고객의 이름을 입력해주세요.")
        private String user;
        @NotEmpty(message = "등록할 매물의 이름을 입력해주세요.")
        private String estate;
        private Range.Price price;
        @NotEmpty(message = "매매 종류를 입력해주세요.")
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
//        @NotEmpty(message = "변경할 고객의 이름을 입력해주세요.")
//        private String user;
//        @NotEmpty(message = "변경할 매물 정보를 입력해주세요.")
//        private String estate;
        private Range.Price price;
        @NotEmpty(message = "변경할 매매 종류를 입력해주세요..")
        private String action;
    }

    @Getter
    public static class SearchReq {
        private String user;
        private String estate;
        private Action action;
        private Range.Date date;
        private Range.Price price;
        private List<Sort.Order> sortOrders;
    }

    public static class DetailResponse {
        private Long id;
        private String user;
        private String estate;
        private Range.Price price;
        private String action;


        public DetailResponse(Bid bid) {
            this.id = bid.getId();
            this.user = bid.getUser().getName();
            this.estate = bid.getEstate().getName();
            this.price = bid.getPriceRange();
            this.action = bid.getAction().name();
        }
    }

    enum SearchOrder {

    }
}
