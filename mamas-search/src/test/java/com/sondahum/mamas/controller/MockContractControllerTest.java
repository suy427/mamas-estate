package com.sondahum.mamas.controller;


import com.sondahum.mamas.domain.bid.BidInfoService;
import com.sondahum.mamas.domain.contract.ContractInfoService;
import com.sondahum.mamas.domain.contract.ContractRepository;
import com.sondahum.mamas.domain.contract.ContractSearchService;
import com.sondahum.mamas.testutil.AbstractMockRequestHelper;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

@RunWith(MockitoJUnitRunner.class)
public class MockContractControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private ContractInfoService infoService;
    @Mock
    private ContractSearchService searchService;
}