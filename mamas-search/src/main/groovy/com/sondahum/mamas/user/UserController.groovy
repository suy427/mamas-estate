package com.sondahum.mamas.user

import com.sondahum.mamas.user.dto.UserDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/user')
class UserController {

    private static final Logger logger =  LoggerFactory.getLogger(this)
    private final UserInfoService userInfoService


    UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService

    }


    @PostMapping('/new')
    UserDto.Response createNewUser(UserDto.CreateReq userDto) {
        userInfoService.createNewUserInfo(userDto)
    }
}
