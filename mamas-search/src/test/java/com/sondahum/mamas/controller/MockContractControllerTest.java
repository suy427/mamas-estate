package com.sondahum.mamas.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sondahum.mamas.domain.contract.ContractInfoService;
import com.sondahum.mamas.domain.contract.ContractSearchService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class MockContractControllerTest {

    @InjectMocks
    private ContractController contractController;
    @Mock
    private ContractSearchService contractSearchService;
    @Mock
    private ContractInfoService contractInfoService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(contractController).build();
    }
}