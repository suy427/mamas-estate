package com.sondahum.mamas.elasticsearch.service

import com.sondahum.mamas.elasticsearch.model.EsDto
import com.sondahum.mamas.elasticsearch.model.SearchOption
import org.elasticsearch.action.search.SearchResponse;

interface EsDao {

    void save(List<EsDto> esDto) // Create
    void delete(List<EsDto> esDto) // Delete
    void update(List<EsDto> esDto) // Update
    String search(SearchOption searchOption) // Retrieve
}