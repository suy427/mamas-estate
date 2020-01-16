package com.sondahum.mamas.elasticsearch.service

import com.sondahum.mamas.elasticsearch.model.EsDto
import com.sondahum.mamas.elasticsearch.model.SearchOption;

interface EsDao {

    void save(EsDto indexingData) // Create
    void delete(EsDto indexingData) // Delete
    void update(EsDto indexingData) // Update
    String search(SearchOption searchOption) // Retrieve
}