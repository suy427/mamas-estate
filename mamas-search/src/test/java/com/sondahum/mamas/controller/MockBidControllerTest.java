package com.sondahum.mamas.controller;

import com.sondahum.mamas.testutil.AbstractMockRequestHelper;
import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.dto.BidDto;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;


public class MockBidControllerTest extends AbstractMockRequestHelper {

    @Test
    public void SuccessCreateBid() throws Exception {
        BidDto.CreateReq dto =
                BidDto.CreateReq.builder()
                        .user("김철수")
                        .estate("로열팰리스 1003호")
                        .action(Action.SELL).build();

        MockHttpServletResponse result = requestPost(
                "/bid",
                requestBody(dto)
        );

        System.out.println(result.getContentAsString());
    }

    @Test
    public void createBidWithBlankName() throws Exception {
        BidDto.CreateReq dto =
                BidDto.CreateReq.builder()
                        .user("")
                        .estate("로열팰리스 1003호")
                        .action(Action.SELL).build();

        MockHttpServletResponse result = requestPost(
                "/bid",
                requestBody(dto)
        );

        System.out.println(result.getContentAsString());
    }

    @Test
    public void createBidWithBlankEstate() throws Exception {
        BidDto.CreateReq dto =
                BidDto.CreateReq.builder()
                        .user("김철수")
                        .estate("")
                        .action(Action.SELL).build();

        MockHttpServletResponse result = requestPost(
                "/bid",
                requestBody(dto)
        );

        System.out.println(result.getContentAsString());
    }

    @Test
    public void createBidWithBlankAction() throws Exception {
        BidDto.CreateReq dto =
                BidDto.CreateReq.builder()
                        .user("김철수")
                        .estate("로열팰리스 1003호")
                        .action(null).build();

        MockHttpServletResponse result = requestPost(
                "/bid",
                requestBody(dto)
        );

        System.out.println(result.getContentAsString());
    }

    @Test
    public void updateBid() throws Exception {
        BidDto.UpdateReq dto = BidDto.UpdateReq.builder()
                .price(buildPrice(1L, 5L))
                .action(Action.BUY).build();

        Bid bid = Bid.builder().action(dto.getAction()).priceRange(dto.getPrice()).build();

        MockHttpServletResponse result = requestPost(
                "/bid",
                requestBody(dto)
        );

        System.out.println(result.getContentAsString());
    }

    Range.Price buildPrice(Long min, Long max) {
        return Range.Price.builder().minimum(min).maximum(max).build();
    }
}