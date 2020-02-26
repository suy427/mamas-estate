package com.sondahum.mamas.user;

import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.user.domain.UserSearchFilter;
import com.sondahum.mamas.user.dto.UserDto;
import com.sondahum.mamas.user.service.UserInfoService;
import com.sondahum.mamas.user.service.UserSearchService;
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
    private final UserSearchService userSearchService;


    UserController(UserInfoService userInfoService, UserSearchService userSearchService) {
        this.userInfoService = userInfoService;
        this.userSearchService = userSearchService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto.Response createNewUser(@RequestBody @Valid UserDto.CreateReq userDto) {
        return userInfoService.createNewUserInfo(userDto);
    }

    @GetMapping
    public Page<UserDto.Response> getUsers( // 이걸로 검색과 전체 유저 불러오기 가능
            @RequestParam(name = "type") final UserSearchFilter filter,
            @RequestParam(name = "value", required = false) final String value,
            final PageRequest pageRequest
    ) {
        return userSearchService.search(filter, value, pageRequest.of()).map(UserDto.Response::new);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserDto.Response getUser(@PathVariable final long id) {
        return userInfoService.getUserById(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserDto.Response updateMyAccount(@PathVariable final long id, @RequestBody final UserDto.UpdateReq dto) {
        return userInfoService.updateUserInfo(id, dto);
    }

}
