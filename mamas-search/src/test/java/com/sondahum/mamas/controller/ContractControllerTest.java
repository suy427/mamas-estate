package com.sondahum.mamas.controller;

import com.sondahum.mamas.AbstractMamasTest;
import com.sondahum.mamas.domain.bid.BidInfoService;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ContractControllerTest extends AbstractMamasTest {

    @InjectMocks
    private final ContractController contractController;
    @Mock
    private BidInfoService bidInfoService;


    public ContractControllerTest(ContractController contractController) {
        this.contractController = contractController;
    }
}