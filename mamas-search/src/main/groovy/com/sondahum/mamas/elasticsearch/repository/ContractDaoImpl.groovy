package com.sondahum.mamas.elasticsearch.repository

import com.sondahum.mamas.elasticsearch.dto.ContractDto
import com.sondahum.mamas.elasticsearch.dto.UserDto
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
class ContractDaoImpl extends EsClientHelper implements ContractDao{

    private final String indexName = 'mamas-contract'

    @Override
    void save(List<ContractDto> contractList) {
        contractList.each { contract ->
            byte[] byteContents = objectMapper.writeValueAsBytes(contract)
            bulkProcessor.add(new IndexRequest(indexName).source(byteContents, XContentType.JSON))
        }
    }

    @Override
    void delete(List<ContractDto> contractList) {
        contractList.each { contract ->
            bulkProcessor.add(new DeleteRequest(indexName, contract.id))
        }
    }

    @Override
    void update(List<ContractDto> contractList) {
        contractList.each { contract ->
            UpdateRequest request = new UpdateRequest(indexName, contract.id) // item을 하나씩 가져와서 얘에 대한 정보를 다 쿼리로 작성..?

            Script script = getUpdateScript(indexName, contract)
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
        SearchResponse response = esClient.searchTemplate(searchTemplateRequest, RequestOptions.DEFAULT).getResponse()

        return response
    }
}
