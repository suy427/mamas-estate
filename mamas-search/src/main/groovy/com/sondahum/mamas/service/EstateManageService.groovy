package com.sondahum.mamas.service

import com.sondahum.mamas.elasticsearch.dto.EstateDto
import com.sondahum.mamas.elasticsearch.dto.SearchOption

interface EstateManageService { // Estate 정보 CRUD, Aggregation 까지

    List<EstateDto> getEstateInformation()
    List<EstateDto> searchEstateData(SearchOption searchOption)
    List<EstateDto> updateEstateDate(List<EstateDto> estateList)
    List<EstateDto> deleteEstateData(List<EstateDto> estateList)
    void createEstateData(List<EstateDto> estateList)

}