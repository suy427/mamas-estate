package com.sondahum.mamas.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.domain.user.UserInfoService;
import com.sondahum.mamas.domain.user.UserSearchService;
import com.sondahum.mamas.domain.user.model.Role;
import com.sondahum.mamas.dto.UserDto;
import com.sondahum.mamas.testutil.TestValueGenerator;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class MockUserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserInfoService userInfoService;
    @Mock
    private UserSearchService userSearchService;

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
                .role(Role.OTHER)
                .build();
        given(userInfoService.createUserInfo(any())).willReturn(dto.toEntity());

        //when
        final MockHttpServletResponse resultAction = requestCreateUser(dto).andReturn().getResponse();

        MatcherAssert.assertThat(resultAction.getStatus(), CoreMatchers.is(200));
    }

    @Test
    public void 유저_업데이트() throws Exception {
        //given
        final UserDto.UpdateReq dto = UserDto.UpdateReq.builder()
                .name("김철순")
                .phone("010-0011-1101")
                .role(Role.AGENT)
                .build();
        final User updatedUser = User.builder()
                .name(dto.getName())
                .phone(dto.getPhone())
                .role(dto.getRole())
                .build();

        given(userInfoService.updateUserInfo(anyLong(), any(UserDto.UpdateReq.class))).willReturn(updatedUser);

        //when
        final MockHttpServletResponse resultAction = requestUpdateUser(dto).andReturn().getResponse();

        MatcherAssert.assertThat(resultAction.getStatus(), CoreMatchers.is(200));
    }

    @Test
    public void 유저_찾기() throws Exception {
        //given
        final List<User> users = new ArrayList<>();
        users.add(TestValueGenerator.userInfoGenerator());
        users.add(TestValueGenerator.userInfoGenerator());
        users.add(TestValueGenerator.userInfoGenerator());

        given(userSearchService.specify(anyString())).willReturn(users);

        //when
        final MockHttpServletResponse resultAction = requestSpecify(anyString()).andReturn().getResponse();

        MatcherAssert.assertThat(resultAction.getStatus(), CoreMatchers.is(200));
    }

    @Test
    public void 유저_soft삭제() throws Exception {
        //given
        final User entity = TestValueGenerator.userInfoGenerator();

        given(userInfoService.deleteUserSoft(anyLong())).willReturn(entity);

        final MockHttpServletResponse resultAction = requestSoftDeleteUser(anyLong()).andReturn().getResponse();

        MatcherAssert.assertThat(resultAction.getStatus(), CoreMatchers.is(200));
    }

    private ResultActions requestSpecify(String query) throws Exception {
        return mockMvc.perform(
                get("/users/specify")
                        .param("query", query)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    private ResultActions requestCreateUser(UserDto.CreateReq dto) throws Exception {
        return mockMvc.perform(
                post("/users/user")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    private ResultActions requestUpdateUser(UserDto.UpdateReq dto) throws Exception {
        return mockMvc.perform(
                put("/users/1")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    private ResultActions requestSoftDeleteUser(long id) throws Exception {
        return mockMvc.perform(
                put("/users/delete/"+id)).andDo(print());
    }

    private ResultActions requestGetUser() throws Exception {
        return mockMvc.perform(get("/user/" + anyLong())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}