package com.sondahum.mamas.controller;

import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.domain.user.UserInfoService;
import com.sondahum.mamas.dto.UserDto;
import com.sondahum.mamas.domain.user.UserInfoDao;
import com.sondahum.mamas.domain.user.UserSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
class UserController {

    private final UserInfoService userInfoService;
    private final UserSearchService userSearchService;



    @PostMapping
    public UserDto.DetailResponse createUser(@RequestBody @Valid UserDto.CreateReq userDto) {
        return new UserDto.DetailResponse(userInfoService.createUserInfo(userDto));
    }

    @GetMapping
    public Page<UserDto.SearchResponse> searchUsers( // 서치 결과는 목록형으로... 그걸 누르면 디테일 정보 확인하게끔
                                                     @RequestParam(name = "query", required = false) final UserDto.SearchReq query,
                                                     final PageRequest pageRequest
    ) {
        return userSearchService.search(query, pageRequest.of(query.getSortOrders())).map(UserDto.SearchResponse::new);
    }

    @GetMapping(value = "/{id}")
    public UserDto.DetailResponse getUserDetail(@PathVariable final long id) {
        return new UserDto.DetailResponse(userInfoService.getUserById(id));
    }

    @PutMapping
    public UserDto.DetailResponse updateUserInfo(@RequestBody final UserDto.UpdateReq dto) {
        return new UserDto.DetailResponse(userInfoService.updateUserInfo(dto));
    }

    @DeleteMapping(value = "/{id}")
    public UserDto.DetailResponse deleteUser(@PathVariable final long id) {
        return new UserDto.DetailResponse(userInfoService.deleteUserInfo(id));
    }

}