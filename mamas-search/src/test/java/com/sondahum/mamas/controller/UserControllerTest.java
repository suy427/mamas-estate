package com.sondahum.mamas.controller;


import com.sondahum.mamas.TestValueGenerator;
import com.sondahum.mamas.domain.bid.BidInfoService;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class UserControllerTest {

    @InjectMocks
    private final UserController userController;
    @Mock
    private BidInfoService bidInfoService;


    public UserControllerTest(UserController userController) {
        this.userController = userController;
    }
}