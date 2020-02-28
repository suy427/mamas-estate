package com.sondahum.mamas.controller;

import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.dto.UserDto;
import com.sondahum.mamas.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
class UserController {

    private static final Logger logger =  LoggerFactory.getLogger(UserController.class);
    private final UserInfoService userInfoService;
//    private final UserSearchService userSearchService;


    UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
//        this.userSearchService = userSearchService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto.DetailResponse createUser(@RequestBody @Valid UserDto.CreateReq userDto) {
        return userInfoService.createUserInfo(userDto);
    }

    @GetMapping
    public Page<UserDto.SearchResponse> searchUsers( // 서치 결과는 목록형으로... 그걸 누르면 디테일 정보 확인하게끔
                                                     @RequestParam(name = "value", required = false) final UserDto.SearchReq query,
                                                     final PageRequest pageRequest
    ) {
        return userInfoService.searchUsers(query, pageRequest.of()).map(UserDto.SearchResponse::new);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserDto.DetailResponse getUserDetail(@PathVariable final long id) {
        return userInfoService.getUserById(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserDto.DetailResponse updateUserInfo(@PathVariable final long id, @RequestBody final UserDto.UpdateReq dto) {
        return userInfoService.updateUserInfo(id, dto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserDto.DetailResponse deleteUser(@PathVariable final long id) {
        return userInfoService.deleteUserInfo(id);
    }

}
