package com.sondahum.mamas.controller;

import com.sondahum.mamas.AbstractTestHelper;
import com.sondahum.mamas.TestValueGenerator;
import com.sondahum.mamas.domain.bid.BidInfoService;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class EstateControllerTest extends AbstractTestHelper {

    @InjectMocks
    private final EstateController estateController;
    @Mock
    private BidInfoService bidInfoService;


    public EstateControllerTest(EstateController estateController) {
        this.estateController = estateController;
    }
}