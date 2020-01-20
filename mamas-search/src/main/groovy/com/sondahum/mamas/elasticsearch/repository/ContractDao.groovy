package com.sondahum.mamas.elasticsearch.repository

import com.sondahum.mamas.elasticsearch.dto.EsDto
import com.sondahum.mamas.elasticsearch.model.SearchOption
import org.elasticsearch.action.delete.DeleteRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.action.search.SearchType
import org.elasticsearch.action.update.UpdateRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.script.ScriptType
import org.elasticsearch.script.mustache.SearchTemplateRequest
import org.springframework.stereotype.Repository

@Repository
class ContractDao extends EsClientHelper implements EsDao {

    @Override
    void save(List<EsDto> esDtoList) {
        esDtoList.each { dto ->
            byte[] byteContents = objectMapper.writeValueAsBytes(dto)
            bulkProcessor.add(new IndexRequest(dto.indexName).source(byteContents, XContentType.JSON))
        }
    }

    @Override
    void delete(List<EsDto> esDtoList) {
        esDtoList.each { dto ->
            bulkProcessor.add(new DeleteRequest(dto.indexName, dto.id))
        }
    }

    @Override
    void update(List<EsDto> esDtoList) {
        esDtoList.each { dto ->
            bulkProcessor.add(new UpdateRequest(dto.indexName, dto.id))
        }
    }

    @Override
    SearchResponse search(SearchOption searchOption) {
        SearchTemplateRequest searchTemplateRequest = new SearchTemplateRequest()
        SearchRequest searchRequest = new SearchRequest(indexInfo.name)
        searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH)
        searchRequest.allowPartialSearchResults(true)

        searchTemplateRequest.setRequest(searchRequest)
        searchTemplateRequest.setScriptType(ScriptType.STORED)
        searchTemplateRequest.setScript('search-template-test1')

        // this is why i make java library. i should make feature that set key of map more flexible
        Map<String, Object> params = searchOption.toMap()
        searchTemplateRequest.setScriptParams(params)

        //SearchTemplateResponse VS SearchResponse 알아보기
        SearchResponse response = client.searchTemplate(searchTemplateRequest, RequestOptions.DEFAULT).getResponse()

        return response
    }
}
