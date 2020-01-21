package com.sondahum.mamas.elasticsearch.repository

import com.sondahum.mamas.elasticsearch.dto.ContractDto
import com.sondahum.mamas.elasticsearch.dto.EstateDto
import com.sondahum.mamas.elasticsearch.model.SearchOption
import org.elasticsearch.action.search.SearchResponse


interface ContractDao {

    void save(List<ContractDto> contractList) // Create
    void delete(List<ContractDto> contractList) // Delete
    void update(List<ContractDto> contractList) // Update
    SearchResponse search(SearchOption searchOption) // Retrieve

}
