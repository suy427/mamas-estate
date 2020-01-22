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
        userDao.retrieve()
    }

    @Override
    List<UserDto> searchUserData(SearchOption searchOption) {
        userDao.search(searchOption)
    }

    @Override
    List<UserDto> updateUserData(List<UserDto> userList) {
        userDao.update(userList)
    }

    @Override
    List<UserDto> deleteUserData(List<UserDto> userList) {
        userDao.delete(userList)
    }

    @Override
    void createUserData(List<UserDto> userList) {
        userDao.save(userList)
    }
}
