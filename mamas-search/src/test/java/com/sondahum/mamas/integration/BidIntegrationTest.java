package com.sondahum.mamas.integration;

import com.sondahum.mamas.common.error.ErrorController;
import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.controller.BidController;
import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.dto.BidDto;
import com.sondahum.mamas.testutil.AbstractMockRequestHelper;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


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
                        .userName("박숙자")
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
                "/bids/3",
                requestBody(dto)
        );

        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(200));
    }


    /*********************************
     *          조회 TEST
     *********************************/
    @Test
    public void 호가정보_조회() throws Exception {
        MockHttpServletResponse result = requestGet("/bids/3");
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(200));
    }


    /*********************************
     *          검색 TEST
     *********************************/
    @Test
    public void 유저로_검색() throws Exception {
        BidDto.SearchReq query = new BidDto.SearchReq();
        query.setUser("박숙자");

       PageRequest pageRequest =
               pageRequestBuilder(1, 5);

       MockHttpServletResponse result =
                requestGet(
                        "/bids/search",
                        requestParameters(query),
                        requestBody(pageRequest)
                );

        System.out.println(result.getContentAsString());
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(200));
    }

    @Test
    public void 매물로_검색() throws Exception {
        BidDto.SearchReq query = new BidDto.SearchReq();
        query.setEstate("세운상가");

        PageRequest pageRequest =
                pageRequestBuilder(1, 5);

        MockHttpServletResponse result =
                requestGet(
                        "/bids/search",
                        requestParameters(query),
                        requestBody(pageRequest)
                );

        System.out.println(result.getContentAsString());
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(200));
    }

    @Test
    public void 액션으로_검색() throws Exception {
        BidDto.SearchReq query = new BidDto.SearchReq();
        query.setAction(Action.SELL);

        PageRequest pageRequest =
                pageRequestBuilder(1, 5);

        MockHttpServletResponse result =
                requestGet(
                        "/bids/search",
                        requestParameters(query),
                        requestBody(pageRequest)
                );

        System.out.println(result.getContentAsString());
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(200));
    }

    @Test
    public void 가격으로_검색() throws Exception {
        BidDto.SearchReq query = new BidDto.SearchReq();
        query.setPrice(Range.Price.builder().minimum(100L).maximum(1000L).build());

        PageRequest pageRequest =
                pageRequestBuilder(1, 5);

        MockHttpServletResponse result =
                requestGet(
                        "/bids/search",
                        requestParameters(query),
                        requestBody(pageRequest)
                );

        System.out.println(result.getContentAsString());
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(200));
    }

    private Range.Price buildPrice(Long min, Long max) {
        return Range.Price.builder().minimum(min).maximum(max).build();
    }

    private PageRequest pageRequestBuilder(int page, int size) {
        PageRequest pageRequest = new PageRequest();

        pageRequest.setSize(size);
        pageRequest.setPage(page);
        pageRequest.setOrderList(orderBuilder()); // todo orderList....

        return pageRequest;
    }

    private List<Sort.Order> orderBuilder() {
        List<Sort.Order> list = new LinkedList<>();

        list.add(new Sort.Order(Sort.Direction.DESC, "name"));
        list.add(new Sort.Order(Sort.Direction.ASC, "min_price"));

        return list;
    }
}