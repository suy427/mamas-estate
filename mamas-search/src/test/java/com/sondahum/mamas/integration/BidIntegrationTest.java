package com.sondahum.mamas.integration;

import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.dto.BidDto;
import com.sondahum.mamas.testutil.AbstractMockRequestHelper;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class BidIntegrationTest extends AbstractMockRequestHelper {


    /*********************************
     *          등록 TEST
     *********************************/
    @Test
    public void 등록성공() throws Exception {
        BidDto.CreateReq dto =
                BidDto.CreateReq.builder()
                        .userName("황성욱")
                        .estateName("세운상가")
                        .action(Action.BUY)
                        .price(Range.Price.builder().minimum(10L).maximum(20L).build())
                        .build();

        MockHttpServletResponse result = requestPost(
                "/bids/bid",
                requestBody(dto)
        );

        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(200));
    }

    @Test
    public void 중복호가() throws Exception {
        BidDto.CreateReq dto =
                BidDto.CreateReq.builder()
                        .userName("황성욱")
                        .estateName("세운상가")
                        .action(Action.BUY)
                        .price(Range.Price.builder().minimum(10L).maximum(20L).build())
                        .build();

        MockHttpServletResponse result = requestPost(
                "/bids/bid",
                requestBody(dto)
        );

        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(400));
    }

    @Test
    public void 사람_이름이_없을때() throws Exception {
        BidDto.CreateReq dto =
                BidDto.CreateReq.builder()
                        .userName("")
                        .estateName("로열팰리스 1003호")
                        .action(Action.SELL).build();

        MockHttpServletResponse result = requestPost(
                "/bids/bid",
                requestBody(dto)
        );

        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(400));
    }

    @Test
    public void 땅_정보가_없을때() throws Exception {
        BidDto.CreateReq dto =
                BidDto.CreateReq.builder()
                        .userName("김철수")
                        .estateName("")
                        .action(Action.SELL).build();

        MockHttpServletResponse result = requestPost(
                "/bids/bid",
                requestBody(dto)
        );

        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(400));
    }

    @Test
    public void 액션이_없을때() throws Exception {
        BidDto.CreateReq dto =
                BidDto.CreateReq.builder()
                        .userName("김철수")
                        .estateName("로열팰리스 1003호")
                        .action(null).build();

        MockHttpServletResponse result = requestPost(
                "/bids/bid",
                requestBody(dto)
        );

        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(400));
    }


    /*********************************
     *          업데이트 TEST
     *********************************/
    @Test
    public void 기본정보_업데이트() throws Exception {
        BidDto.UpdateReq dto = BidDto.UpdateReq.builder()
                .price(buildPrice(1L, 5L))
                .action(Action.BUY).build();

        Bid bid = Bid.builder().action(dto.getAction()).priceRange(dto.getPrice()).build();

        MockHttpServletResponse result = requestPut(
                "/bids/1",
                requestBody(dto)
        );

        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(200));
    }


    /*********************************
     *          조회 TEST
     *********************************/
    @Test
    public void 호가정보_조회() throws Exception {
        MockHttpServletResponse result = requestGet("/bids/1");
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(200));
    }


    /*********************************
     *          검색 TEST
     *********************************/
    @Test
    public void 유저로_검색() throws Exception {
        BidDto.SearchReq query = BidDto.SearchReq.builder()
                .user("박숙자").build();


    }

    @Test
    public void 매물로_검색() throws Exception {
        BidDto.SearchReq query = BidDto.SearchReq.builder()
                .estate("세운상가").build();

    }

    @Test
    public void 액션으로_검색() throws Exception {
        BidDto.SearchReq query = BidDto.SearchReq.builder()
                .action(Action.BUY)
                .page(1)
                .size(10)
                .orders(orderBuilder())
                .build();

        MockHttpServletResponse result =
                requestGet(
                        "/bids",
                        requestParameters(query)
                );

        System.out.println(result.getContentAsString());
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(200));
    }

    @Test
    public void 가격으로_검색() throws Exception {
        BidDto.SearchReq query = BidDto.SearchReq.builder()
                .price(Range.Price.builder().minimum(1L).maximum(100L).build()).build();


    }

    @Test
    public void 다중_조건_검색() throws Exception {
        MockHttpServletResponse result = requestSearch();

        System.out.println(result.getContentAsString());
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(200));
    }

    private Range.Price buildPrice(Long min, Long max) {
        return Range.Price.builder().minimum(min).maximum(max).build();
    }

    private List<BidDto.SortOrder> orderBuilder() {
        List<BidDto.SortOrder> list = new LinkedList<>();

        list.add(BidDto.SortOrder.builder().direction("ASC").property("min_price").build());
        list.add(BidDto.SortOrder.builder().direction("DESC").property("user").build());
        list.add(BidDto.SortOrder.builder().direction("ASC").property("estate").build());

        return list;
    }

    private MockHttpServletResponse requestSearch() throws Exception {
        return mockMvc.perform(
                get("/bids")
                        .requestAttr("user", "황성욱")
                        .requestAttr("estate", "세운상가")
                        .requestAttr("action", Action.BUY.name())
                        .requestAttr("page", "1")
                        .requestAttr("size", "10")
                        .requestAttr("orders[0]", "ACS, name")
                        .requestAttr("orders[1]", "ACS, min_price")
                        .requestAttr("orders[2]", "DESC, estate")
        )
                .andDo(print())
                .andReturn().getResponse();
    }
}