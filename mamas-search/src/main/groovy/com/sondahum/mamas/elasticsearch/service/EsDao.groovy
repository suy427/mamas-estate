package com.sondahum.mamas.elasticsearch.service

import com.sondahum.mamas.elasticsearch.model.IndexingData
import com.sondahum.mamas.elasticsearch.model.SearchOption;

interface EsDao {

    void save(IndexingData indexingData) // C
    void delete(IndexingData indexingData) // D
    void update(IndexingData indexingData) // U
    String search(SearchOption searchOption) // R
}