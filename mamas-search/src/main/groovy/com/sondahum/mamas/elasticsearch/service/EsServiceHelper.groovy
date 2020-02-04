package com.sondahum.mamas.elasticsearch.service

import com.sondahum.mamas.elasticsearch.dto.EsDto
import org.elasticsearch.search.SearchHits
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class EsServiceHelper {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName())

    List<EsDto> getSearchResult(SearchHits searchHits) {
        List<EsDto> dtoList = searchHits?.collect { hit ->
            String hitJson = hit.sourceAsString
            // 팩토리 메소드 디자인 패턴 적용했는데 제대로 된걸까...? 이상하게 느껴진다...
            EsDto dto = matchDto(hitJson)
            return dto

        } ?: []
        return dtoList
    }

    protected abstract EsDto matchDto(String hitJson)
}
