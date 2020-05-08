package com.sondahum.mamas.controller;

import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.domain.user.UserInfoService;
import com.sondahum.mamas.dto.UserDto;
import com.sondahum.mamas.domain.user.UserSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
class UserController {

    private final UserInfoService userInfoService;
    private final UserSearchService userSearchService;


    @PostMapping(value = "/user")
    public UserDto.DetailForm createUser(@RequestBody @Valid UserDto.CreateReq userDto) {
        return new UserDto.DetailForm(userInfoService.createUserInfo(userDto));
    }

    @PostMapping
    public List<UserDto.SimpleForm> specifyUser(String query) {
        return userSearchService.specify(query).stream()
                .map(UserDto.SimpleForm::new)
                .collect(Collectors.toList());
    }

    @PostMapping // 정보가 없는 새로운 유저일 경우 입력 form채워서 만듦.
    public UserDto.SimpleForm setUser(@RequestBody @Valid UserDto.CreateReq userDto) {
        return new UserDto.SimpleForm(userInfoService.createUserInfo(userDto));
    }

    @GetMapping
    public Page<UserDto.SimpleForm> searchUsers(UserDto.SearchReq query, PageRequest pageRequest) {
        return userSearchService.search(query, pageRequest.of(query.getSortOrders())).map(UserDto.SimpleForm::new);
    }

    @GetMapping(value = "/{id}")
    public UserDto.DetailForm getUserDetail(@PathVariable final long id) {
        return new UserDto.DetailForm(userInfoService.getUserById(id));
    }

    @PutMapping
    public UserDto.DetailForm updateUserInfo(@RequestBody final UserDto.UpdateReq dto) {
        return new UserDto.DetailForm(userInfoService.updateUserInfo(dto));
    }

    @PutMapping(value = "/{id}")
    public UserDto.DetailForm deleteUserSoft(@PathVariable final long id) {
        return new UserDto.DetailForm(userInfoService.deleteUserSoft(id));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUserHard(@PathVariable final long id) {
        userInfoService.deleteUserHard(id);
    }

}