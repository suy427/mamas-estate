package com.sondahum.mamas.controller;


import com.sondahum.mamas.TestValueGenerator;
import com.sondahum.mamas.domain.bid.BidInfoService;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class Mock {

    @InjectMocks
    private final EstateController estateController;
    @org.mockito.Mock
    private BidInfoService bidInfoService;


    public Mock(EstateController estateController) {
        this.estateController = estateController;
    }
}