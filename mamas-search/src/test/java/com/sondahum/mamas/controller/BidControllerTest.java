package com.sondahum.mamas.controller;

import com.sondahum.mamas.AbstractMockRequestHelper;
import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.dto.BidDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class BidControllerTest extends AbstractMockRequestHelper {

    @InjectMocks
    private BidController bidController;
//    @Mock
//    private BidInfoService bidInfoService;
//    @Mock
//    private BidRepository bidRepository;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(bidController).build();
    }

    @Test
    public void SuccessCreateBid() throws Exception {
        Map<String, String> requestBody =  new LinkedHashMap<>();

        requestGet(
                "/bid",
                bodyValues()
        );

        ResultActions resultActions = requestCreateBid(dto);
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void createBidWithBlankName() throws Exception {
        BidDto.CreateReq dto =
                BidDto.CreateReq.builder()
                        .user("")
                        .estate("로열팰리스 1003호")
                        .action(Action.SELL).build();

        ResultActions resultActions = requestCreateBid(dto);
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    public void createBidWithBlankEstate() throws Exception {
        BidDto.CreateReq dto =
                BidDto.CreateReq.builder()
                        .user("김철수")
                        .estate("")
                        .action(Action.SELL).build();

        ResultActions resultActions = requestCreateBid(dto);
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    public void createBidWithBlankAction() throws Exception {
        BidDto.CreateReq dto =
                BidDto.CreateReq.builder()
                        .user("김철수")
                        .estate("로열팰리스 1003호")
                        .action(null).build();

        ResultActions resultActions = requestCreateBid(dto);
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    public void updateBid() throws Exception {
        BidDto.UpdateReq dto = BidDto.UpdateReq.builder()
                .price(buildPrice(1L, 5L))
                .action(Action.BUY).build();

        Bid bid = Bid.builder().action(dto.getAction()).priceRange(dto.getPrice()).build();


//        given(bidInfoService.updateBidInfo(anyLong(), any(BidDto.UpdateReq.class))).willReturn(bid);

        //when
        final ResultActions resultActions = requestUpdateBid(dto);
    }


    private ResultActions requestCreateBid(BidDto.CreateReq dto) throws Exception {
        return mockMvc.perform(post("/bid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andDo(print());
    }


    private ResultActions requestUpdateBid(BidDto.UpdateReq dto) throws Exception {
        return mockMvc.perform(put("/bid/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andDo(print());
    }


    private ResultActions requestDeleteBid(BidDto.UpdateReq dto) throws Exception {
        return mockMvc.perform(delete("/bid/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andDo(print());
    }


    private ResultActions requestGetBid(BidDto.UpdateReq dto) throws Exception {
        return mockMvc.perform(get("/bid/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andDo(print());
    }


    Range.Price buildPrice(Long min, Long max) {
        return Range.Price.builder().minimum(min).maximum(max).build();
    }
}