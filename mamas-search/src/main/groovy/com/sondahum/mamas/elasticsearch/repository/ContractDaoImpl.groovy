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
import org.elasticsearch.common.unit.TimeValue
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.index.query.MatchAllQueryBuilder
import org.elasticsearch.script.Script
import org.elasticsearch.script.ScriptType
import org.elasticsearch.script.mustache.SearchTemplateRequest
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.SearchHits
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.stereotype.Repository

import java.text.SimpleDateFormat

@Repository
class ContractDaoImpl extends EsClientHelper implements ContractDao{

    private final String indexName = 'mamas-contract'

    @Override
    List<ContractDto> retrieve() {
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
        List<ContractDto> totalContractList = getSearchResult(searchHits)

        return totalContractList
    }

    @Override
    void save(List<ContractDto> contractList) {
        contractList.each { contract ->
            byte[] byteContents = objectMapper.writeValueAsBytes(contract)
            bulkProcessor.add(new IndexRequest(indexName).source(byteContents, XContentType.JSON))
        }
    }

    @Override
    List<ContractDto> delete(List<ContractDto> contractList) {
        contractList.each { contract ->
            bulkProcessor.add(new DeleteRequest(indexName, contract.id))
        }
    }

    @Override
    List<ContractDto> update(List<ContractDto> contractList) {
        contractList.each { contract ->
            UpdateRequest request = new UpdateRequest(indexName, contract.id) // item을 하나씩 가져와서 얘에 대한 정보를 다 쿼리로 작성..?

            Script script = getUpdateScript(indexName, contract)
            request.script(script)

            bulkProcessor.add(request)
        }
    }

    @Override
    List<ContractDto> search(SearchOption searchOption) {
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
        SearchHits searchHits = response.getHits()
        List<ContractDto> searchResultList = getSearchResult(searchHits)

        return searchResultList
    }

    private List<ContractDto> getSearchResult(SearchHits searchHits) {
        List<ContractDto> contractList = searchHits?.collect { hit ->
            Map<String, Object> _source = hit.sourceAsMap
            Map<String, Object> _highlighted = hit.highlightFields.collectEntries {
                field, highlightField -> [field, highlightField.fragments?.join()]} as Map<String, Object> // TODO 이부분 확실히 공부

            return new ContractDto( // TODO 이부분 직접 바꾸지말고 유연하게 다시 짜볼것.
                    id: _source.get("id"),
                    estate: _highlighted.get("estate.for_search") ?: _source.get("estate"),
                    seller: _highlighted.get("seller") ?: _source.get("seller"),
                    buyer: _highlighted.get("buyer") ?: _source.get("buyer"),
                    price: _highlighted.get("price") ?: _source.get("price"),
                    agent: _highlighted.get("agent") ?: _source.get("agent"),
                    contractedDate: new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(_source.get("contractedDate").toString())
            )
        } ?: []
        return contractList
    }
}
