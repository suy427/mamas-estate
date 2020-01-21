package com.sondahum.mamas.elasticsearch.repository

import com.sondahum.mamas.elasticsearch.dto.EsDto
import com.sondahum.mamas.elasticsearch.model.SearchOption
import org.elasticsearch.action.search.SearchResponse;

interface EsDao {

    void save(EsDto esDto) // Create
    void delete(EsDto esDto) // Delete
    void update(EsDto esDto) // Update
    SearchResponse search(SearchOption searchOption) // Retrieve
}