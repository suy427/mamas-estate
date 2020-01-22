package com.sondahum.mamas.elasticsearch.repository

import com.sondahum.mamas.elasticsearch.dto.ContractDto
import com.sondahum.mamas.elasticsearch.dto.EstateDto
import com.sondahum.mamas.elasticsearch.model.SearchOption
import org.elasticsearch.action.search.SearchResponse


interface ContractDao {

    List<ContractDto> retrieve()
    void save(List<ContractDto> contractList) // Create
    List<ContractDto> delete(List<ContractDto> contractList) // Delete
    List<ContractDto> update(List<ContractDto> contractList) // Update
    List<ContractDto> search(SearchOption searchOption) // Retrieve

}
