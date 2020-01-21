package com.sondahum.mamas.elasticsearch.repository

import com.sondahum.mamas.elasticsearch.dto.UserDto
import com.sondahum.mamas.elasticsearch.model.SearchOption
import org.elasticsearch.action.search.SearchResponse

interface ClientDao {

    void save(UserDto esDto) // Create
    void delete(UserDto esDto) // Delete
    void update(UserDto esDto) // Update
    SearchResponse search(SearchOption searchOption) // Retrieve

}
