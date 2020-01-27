package com.sondahum.mamas.elasticsearch.repository

import com.sondahum.mamas.elasticsearch.dto.ContractDto
import com.sondahum.mamas.elasticsearch.dto.EstateDto
import com.sondahum.mamas.elasticsearch.dto.UserDto
import com.sondahum.mamas.elasticsearch.model.SearchOption
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.elasticsearch.action.delete.DeleteRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.action.search.SearchType
import org.elasticsearch.action.update.UpdateRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.common.unit.TimeValue
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.index.query.MatchAllQueryBuilder
import org.elasticsearch.script.Script
import org.elasticsearch.script.ScriptType
import org.elasticsearch.script.mustache.SearchTemplateRequest
import org.elasticsearch.search.SearchHits
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.stereotype.Repository



@Repository
class EstateDaoImpl extends EsClientHelper implements EstateDao{

    private final String indexName = 'mamas-estate'

    @Override
    List<EstateDto> retrieve() {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
        searchSourceBuilder.query(new MatchAllQueryBuilder())
        searchSourceBuilder.size(1000)

        SearchRequest request = new SearchRequest(indexName)
        request.searchType(SearchType.DFS_QUERY_THEN_FETCH)
        request.allowPartialSearchResults(false)
        request.scroll(TimeValue.timeValueMinutes(1L))
        request.source(searchSourceBuilder)

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT)
        SearchHits searchHits = response.getHits()
        List<EstateDto> totalEstateList = getSearchResult(searchHits)

        return totalEstateList
    }

    @Override
    void save(List<EstateDto> estateList) {
        estateList.each { estate ->
            byte[] byteContents = objectMapper.writeValueAsBytes(estate)
            bulkProcessor.add(new IndexRequest(indexName).source(byteContents, XContentType.JSON))
        }
    }

    @Override
    List<EstateDto> delete(List<EstateDto> estateList) {
        estateList.each { estate ->
            bulkProcessor.add(new DeleteRequest(indexName, estate.id))
        }
    }

    @Override
    List<EstateDto> update(List<EstateDto> estateList) {
        estateList.each { estate ->
            UpdateRequest request = new UpdateRequest(indexName, estate.id) // item을 하나씩 가져와서 얘에 대한 정보를 다 쿼리로 작성..?

            Script script = getUpdateScript(indexName, estate)
            request.script(script)

            bulkProcessor.add(request)
        }
    }

    @Override
    List<EstateDto> search(SearchOption searchOption) {
        SearchTemplateRequest searchTemplateRequest = new SearchTemplateRequest()
        SearchRequest searchRequest = new SearchRequest(indexName)
        searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH)
        searchRequest.allowPartialSearchResults(true)

        searchTemplateRequest.setRequest(searchRequest)
        searchTemplateRequest.setScriptType(ScriptType.STORED)
        searchTemplateRequest.setScript('search-template-test1')

        // this is why i make java library. i should make feature that set key of map more flexible
        Map<String, Object> params = new JsonSlurper().parseText(JsonOutput.toJson(searchOption))
//        Map<String, Object> params = searchOption.toMap()
        searchTemplateRequest.setScriptParams(params)

        //SearchTemplateResponse VS SearchResponse 알아보기
        SearchResponse response = esClient.searchTemplate(searchTemplateRequest, RequestOptions.DEFAULT).getResponse()
        SearchHits searchHits = response.getHits()
        List<EstateDto> searchResultList = getSearchResult(searchHits)

        return searchResultList
    }

    private List<EstateDto> getSearchResult(SearchHits searchHits) {
        List<EstateDto> estateList = searchHits?.collect { hit ->
            Map<String, Object> _source = hit.sourceAsMap
            Map<String, Object> _highlighted = hit.highlightFields.collectEntries {
                field, highlightField -> [field, highlightField.fragments?.join()]} as Map<String, Object> // TODO 이부분 확실히 공부

            return new EstateDto(
                    id: _source.get("id"),
                    name: _highlighted.get("name") ?: _source.get("name"),
                    address: _highlighted.get("address") ?: _source.get("address"),
                    productType: _highlighted.get("productType") ?: _source.get("productType"),
                    contractType: _highlighted.get("contractType") ?: _source.get("contractType"),
                    price: _highlighted.get("price") ?: _source.get("price"),
            )
        } ?: []
        return estateList
    }
}
