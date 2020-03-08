package com.sondahum.mamas.controller;

import com.sondahum.mamas.AbstractMamasTest;
import com.sondahum.mamas.domain.bid.BidInfoService;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class EstateControllerTest extends AbstractMamasTest {

    @InjectMocks
    private final EstateController estateController;
    @Mock
    private BidInfoService bidInfoService;


    public EstateControllerTest(EstateController estateController) {
        this.estateController = estateController;
    }
}