package com.sondahum.mamas.controller;

import com.sondahum.mamas.domain.bid.BidInfoService;
import com.sondahum.mamas.domain.bid.BidRepository;
import com.sondahum.mamas.domain.bid.BidSearchService;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(MockitoJUnitRunner.class)
public class MockBidControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private BidSearchService searchService;
    @Mock
    private BidInfoService infoService;


}