package com.sondahum.mamas.integration;

import com.sondahum.mamas.domain.bid.BidRepository;
import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.dto.BidDto;
import com.sondahum.mamas.testutil.AbstractMockRequestHelper;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;


public class BidIntegrationTest extends AbstractMockRequestHelper {


    /*********************************
     *          등록 TEST
     *********************************/
    @Test
    public void 등록성공() throws Exception {
        BidDto.CreateReq dto =
                BidDto.CreateReq.builder()
                        .user("김철수")
                        .estate("로열팰리스 1003호")
                        .action(Action.SELL).build();

        MockHttpServletResponse result = requestPost(
                "/bid",
                requestBody(dto)
        );

        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(200));
    }

    @Test
    public void 사람_이름이_없을때() throws Exception {
        BidDto.CreateReq dto =
                BidDto.CreateReq.builder()
                        .user("")
                        .estate("로열팰리스 1003호")
                        .action(Action.SELL).build();

        MockHttpServletResponse result = requestPost(
                "/bid",
                requestBody(dto)
        );

        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(400));
    }

    @Test
    public void 땅_정보가_없을때() throws Exception {
        BidDto.CreateReq dto =
                BidDto.CreateReq.builder()
                        .user("김철수")
                        .estate("")
                        .action(Action.SELL).build();

        MockHttpServletResponse result = requestPost(
                "/bid",
                requestBody(dto)
        );

        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(400));
    }

    @Test
    public void 액션이_없을때() throws Exception {
        BidDto.CreateReq dto =
                BidDto.CreateReq.builder()
                        .user("김철수")
                        .estate("로열팰리스 1003호")
                        .action(null).build();

        MockHttpServletResponse result = requestPost(
                "/bid",
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

        MockHttpServletResponse result = requestPost(
                "/bid",
                requestBody(dto)
        );

        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(400));
    }


    /*********************************
     *          조회 TEST
     *********************************/
//    @Test
//    public void 호가정보_조회() throws Exception {
//        MockHttpServletResponse result = requestGet("/bid/"+1);
//
//        System.out.println(result.getContentAsString());
//        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(200));
//    }
//
//
//    /*********************************
//     *          검색 TEST
//     *********************************/
//    @Test
//    public void 유저로_검색() throws Exception {
//        BidDto.SearchReq query = BidDto.SearchReq.builder()
//                .user("김철수").build();
//
//       Map<String, String> pageRequest =
//               pageRequestBuilder("1", "5", "ASC");
//
//       MockHttpServletResponse result =
//                requestGet(
//                        "/bid",
//                        requestParameters(query),
//                        requestParameters(pageRequest)
//                );
//
//        System.out.println(result.getContentAsString());
//        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(200));
//    }
//
//    @Test
//    public void 매물로_검색() throws Exception {
//        BidDto.SearchReq query = BidDto.SearchReq.builder()
//                .estate("행복아파트").build();
//
//        Map<String, String> pageRequest =
//                pageRequestBuilder("1", "5", "ASC");
//
//        MockHttpServletResponse result =
//                requestGet(
//                        "/bid",
//                        requestParameters(query),
//                        requestParameters(pageRequest)
//                );
//
//        System.out.println(result.getContentAsString());
//        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(200));
//    }
//
//    @Test
//    public void 액션으로_검색() throws Exception {
//        BidDto.SearchReq query = BidDto.SearchReq.builder()
//                .action(Action.BUY).build();
//
//        Map<String, String> pageRequest =
//                pageRequestBuilder("1", "5", "ASC");
//
//        MockHttpServletResponse result =
//                requestGet(
//                        "/bid",
//                        requestParameters(query),
//                        requestParameters(pageRequest)
//                );
//
//        System.out.println(result.getContentAsString());
//        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(200));
//    }
//
//    @Test
//    public void 가격으로_검색() throws Exception {
//        BidDto.SearchReq query = BidDto.SearchReq.builder()
//                .price(buildPrice(100L, 1000L)).build();
//
//        Map<String, String> pageRequest =
//                pageRequestBuilder("1", "5", "ASC");
//
//        MockHttpServletResponse result =
//                requestGet(
//                        "/bid",
//                        requestParameters(query),
//                        requestParameters(pageRequest)
//                );
//
//        System.out.println(result.getContentAsString());
//        MatcherAssert.assertThat(result.getStatus(), CoreMatchers.is(200));
//    }

    private Range.Price buildPrice(Long min, Long max) {
        return Range.Price.builder().minimum(min).maximum(max).build();
    }

    private Map<String, String> pageRequestBuilder(String page, String size, String direction) {
        Map<String, String> pageRequest= new LinkedHashMap<>();
        pageRequest.put("page", page);
        pageRequest.put("size", size);
        pageRequest.put("direction", direction);

        return pageRequest;
    }
}