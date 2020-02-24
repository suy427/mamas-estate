package com.sondahum.mamas.user;

import com.sondahum.mamas.user.dto.UserDto;
import com.sondahum.mamas.user.service.UserInfoService;
import com.sondahum.mamas.user.service.UserSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/new")
    UserDto.Response createNewUser(UserDto.CreateReq userDto) {
        return userInfoService.createNewUserInfo(userDto);
    }
}
