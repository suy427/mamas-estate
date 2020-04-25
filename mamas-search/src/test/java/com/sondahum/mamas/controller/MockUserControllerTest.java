package com.sondahum.mamas.controller;


import com.sondahum.mamas.domain.user.UserInfoService;
import com.sondahum.mamas.domain.user.UserSearchService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(MockitoJUnitRunner.class)
public class MockUserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private UserInfoService infoService;
    @Mock
    private UserSearchService searchService;

    @Before
    public void setup() {

    }
}