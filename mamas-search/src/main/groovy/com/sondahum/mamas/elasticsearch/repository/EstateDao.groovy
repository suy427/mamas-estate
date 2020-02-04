package com.sondahum.mamas.elasticsearch.repository


import com.sondahum.mamas.elasticsearch.dto.EstateDto
import com.sondahum.mamas.elasticsearch.dto.SearchOption

interface EstateDao {

    List<EstateDto> retrieve()
    void save(List<EstateDto> estateList) // Create
    List<EstateDto> delete(List<EstateDto> estateList) // Delete
    List<EstateDto> update(List<EstateDto> estateList) // Update
    List<EstateDto> search(SearchOption searchOption) // Retrieve
}