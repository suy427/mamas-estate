package com.sondahum.mamas.integration;

import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.common.model.SortOrder;
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
                .build();

        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(1);
        pageRequest.setSize(10);
        pageRequest.setSort(orderBuilder());


        MockHttpServletResponse result =
                requestGet(
                        "/bids",
                        requestParameters(query),
                        requestParameters(pageRequest)
                );

        System.out.println(result.getContentAsString());
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(200));
    }

    @Test
    public void 가격으로_검색() throws Exception {
        BidDto.SearchReq query = BidDto.SearchReq.builder()
                .price(Range.Price.builder().minimum(1L).maximum(100L).build()).build();

        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(1);
        pageRequest.setSize(10);
        pageRequest.setSort(orderBuilder());


        MockHttpServletResponse result =
                requestGet(
                        "/bids",
                        requestParameters(query),
                        requestParameters(pageRequest)
                );

        System.out.println(result.getContentAsString());
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(200));
    }

    @Test
    public void 다중_조건_검색() throws Exception {
        BidDto.SearchReq query = BidDto.SearchReq.builder()
                .user("박숙자").action(Action.BUY).estate("세운상가").build();

        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(1);
        pageRequest.setSize(10);
        pageRequest.setSort(orderBuilder());


        MockHttpServletResponse result =
                requestGet(
                        "/bids",
                        requestParameters(query),
                        requestParameters(pageRequest)
                );

        System.out.println(result.getContentAsString());
        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(200));
    }

    private Range.Price buildPrice(Long min, Long max) {
        return Range.Price.builder().minimum(min).maximum(max).build();
    }

    private List<SortOrder> orderBuilder() {
        List<SortOrder> list = new LinkedList<>();

        list.add(SortOrder.builder().direction("ASC").property("min_price").build());
        list.add(SortOrder.builder().direction("DESC").property("user").build());
        list.add(SortOrder.builder().direction("ASC").property("estate").build());

        return list;
    }

//    private MockHttpServletResponse requestSearch() throws Exception {
//        return mockMvc.perform(
//                get("/bids")
//                        .param("user", "박숙자")
//                        .param("estate", "세운상가")
//                        .param("action", Action.BUY.name())
//                        .param("page", "1")
//                        .param("size", "10")
//                        .param("sort[0].direction", "DESC")
//                        .param("sort[0].property", "name")
//                        .param("sort[1].direction", "ASC")
//                        .param("sort[1].property", "min_price")
//                        .param("sort[2].direction", "ASC")
//                        .param("sort[2].property", "estate")
//        )
//                .andDo(print())
//                .andReturn().getResponse();
//    }
}