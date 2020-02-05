package com.sondahum.mamas.elasticsearch.repository


import com.sondahum.mamas.elasticsearch.dto.UserDto
import com.sondahum.mamas.elasticsearch.dto.SearchOption

interface UserDao {

    List<UserDto> retrieve()
    void save(List<UserDto> userList) // Create
    List<UserDto> delete(List<UserDto> userList) // Delete
    List<UserDto> update(List<UserDto> userList) // Update
    List<UserDto> search(SearchOption searchOption) // Retrieve

}
