package com.sondahum.mamas.user

import com.sondahum.mamas.user.dto.UserDto
import org.springframework.data.domain.Page

interface UserInfoService {

    UserDto.Response createNewUserInfo(UserDto.CreateReq userDto)
    UserDto.Response updateUserInfo(UserDto.UpdateReq userDto)
    UserDto.Response deleteUserInfo(Long id)
    Page<UserDto.Response> getUserInfoList()
    Page<UserDto.Response> searchUser()
}