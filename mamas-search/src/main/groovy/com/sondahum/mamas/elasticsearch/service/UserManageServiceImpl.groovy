package com.sondahum.mamas.elasticsearch.service

import com.sondahum.mamas.elasticsearch.dto.UserDto
import com.sondahum.mamas.elasticsearch.model.SearchOption
import com.sondahum.mamas.elasticsearch.repository.UserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserManageServiceImpl implements UserManageService{

    @Autowired
    UserDao userDao

    @Override
    List<UserDto> getUserInformation() {
        return null
    }

    @Override
    List<UserDto> searchUserData(SearchOption searchOption) {
        return null
    }

    @Override
    List<UserDto> updateUserData(List<UserDto> userList) {
        return null
    }

    @Override
    List<UserDto> deleteUserData(List<UserDto> userList) {
        return null
    }

    @Override
    void createUserData(List<UserDto> userList) {

    }
}
