package com.sondahum.mamas.elasticsearch.repository

import com.sondahum.mamas.elasticsearch.dto.UserDto
import com.sondahum.mamas.elasticsearch.model.SearchOption
import org.elasticsearch.action.search.SearchResponse

interface UserDao {

    void save(List<UserDto> userList) // Create
    void delete(List<UserDto> userList) // Delete
    void update(List<UserDto> userList) // Update
    SearchResponse search(SearchOption searchOption) // Retrieve

}
