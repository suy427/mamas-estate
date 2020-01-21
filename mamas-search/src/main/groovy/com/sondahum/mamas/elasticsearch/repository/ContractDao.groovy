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
import org.elasticsearch.script.Script
import org.elasticsearch.script.ScriptType
import org.elasticsearch.script.mustache.SearchTemplateRequest
import org.springframework.stereotype.Repository

@Repository
class ContractDao extends EsClientHelper implements EsDao {

    private final String indexName = 'mamas-contract'

    @Override
    void save(EsDto esDto) {
        esDto.getItemList().each { dto ->
            byte[] byteContents = objectMapper.writeValueAsBytes(dto)
            bulkProcessor.add(new IndexRequest(indexName).source(byteContents, XContentType.JSON))
        }
    }

    @Override
    void delete(EsDto esDto) {
        esDto.getItemList().each { dto ->
            bulkProcessor.add(new DeleteRequest(indexName, dto.id))
        }
    }

    @Override
    void update(EsDto esDto) {
        esDto.getItemList().each { item ->
            UpdateRequest request = new UpdateRequest(indexName, item.id) // item을 하나씩 가져와서 얘에 대한 정보를 다 쿼리로 작성..?

            Script script = getUpdateScript(indexName, esDto)
            request.script(script)

            bulkProcessor.add(request)
        }
    }

    @Override
    SearchResponse search(SearchOption searchOption) {
        SearchTemplateRequest searchTemplateRequest = new SearchTemplateRequest()
        SearchRequest searchRequest = new SearchRequest(indexName)
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
