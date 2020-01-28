package com.sondahum.mamas.elasticsearch.service

import com.sondahum.mamas.elasticsearch.dto.UserDto
import com.sondahum.mamas.elasticsearch.model.SearchOption

interface UserManageService { // 유저정보 CRUD, Aggregation 까지

    List<UserDto> getUserInformation()
    List<UserDto> searchUserData(SearchOption searchOption)
    List<UserDto> updateUserData(List<UserDto> userList)
    List<UserDto> deleteUserData(List<UserDto> userList)
    void createUserData(List<UserDto> userList)

}