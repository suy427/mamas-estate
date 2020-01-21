package com.sondahum.mamas.elasticsearch.repository

import com.sondahum.mamas.elasticsearch.dto.EstateDto
import com.sondahum.mamas.elasticsearch.model.SearchOption
import org.elasticsearch.action.search.SearchResponse;

interface EstateDao {

    void save(List<EstateDto> estateList) // Create
    void delete(List<EstateDto> estateList) // Delete
    void update(List<EstateDto> estateList) // Update
    SearchResponse search(SearchOption searchOption) // Retrieve
}