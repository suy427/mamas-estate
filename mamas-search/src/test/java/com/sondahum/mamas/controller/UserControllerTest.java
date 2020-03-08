package com.sondahum.mamas.controller;

import com.sondahum.mamas.AbstractMamasTest;
import com.sondahum.mamas.domain.bid.BidInfoService;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class UserControllerTest extends AbstractMamasTest {

    @InjectMocks
    private final UserController userController;
    @Mock
    private BidInfoService bidInfoService;


    public UserControllerTest(UserController userController) {
        this.userController = userController;
    }
}