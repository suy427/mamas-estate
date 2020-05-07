package com.sondahum.mamas.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sondahum.mamas.MamasEstateApplicationStarter;
import com.sondahum.mamas.domain.bid.BidInfoService;
import com.sondahum.mamas.domain.contract.ContractInfoService;
import com.sondahum.mamas.domain.estate.EstateInfoService;
import com.sondahum.mamas.domain.estate.EstateSearchService;
import com.sondahum.mamas.domain.user.UserInfoService;
import com.sondahum.mamas.domain.user.UserSearchService;
import com.sondahum.mamas.domain.user.model.Role;
import com.sondahum.mamas.dto.UserDto;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class MockUserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock private UserInfoService userInfoService;
    @Mock private EstateInfoService estateInfoService;
    @Mock private ContractInfoService contractInfoService;
    @Mock private BidInfoService bidInfoService;
    @Mock private UserSearchService userSearchService;
    @Mock private EstateSearchService estateSearchService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void 최초_유저정보_등록() throws Exception {
        //given
        final UserDto.CreateReq dto = UserDto.CreateReq.builder()
                .name("김철수")
                .phone("101-0011-1101")
                .role("OTHER")
                .build();
        given(userInfoService.createUserInfo(any())).willReturn(dto.toEntity());

        //when
        final MockHttpServletResponse resultActions = requestCreateUser(dto).andReturn().getResponse();

        MatcherAssert.assertThat(resultActions.getStatus(), CoreMatchers.is(200));
    }

    private ResultActions requestCreateUser(UserDto.CreateReq dto) throws Exception {
        return mockMvc.perform(
                post("/user")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    private ResultActions requestUpdateUser(UserDto.UpdateReq dto) throws Exception {
        return mockMvc.perform(
                put("/user")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    private ResultActions requestGetUser() throws Exception {
        return mockMvc.perform(get("/user/" + anyLong())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}