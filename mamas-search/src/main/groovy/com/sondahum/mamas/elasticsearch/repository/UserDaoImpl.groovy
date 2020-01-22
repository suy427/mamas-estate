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
import org.elasticsearch.search.SearchHits
import org.springframework.stereotype.Repository

import java.text.SimpleDateFormat

@Repository
class UserDaoImpl extends EsClientHelper implements UserDao {

    private final String indexName = 'mamas-user'

    @Override
    void save(List<UserDto> userList) {
        userList.each { user ->
            byte[] byteContents = objectMapper.writeValueAsBytes(user)
            bulkProcessor.add(new IndexRequest(indexName).source(byteContents, XContentType.JSON))
        }
    }

    @Override
    List<UserDto> delete(List<UserDto> userList) {
        userList.each { user ->
            bulkProcessor.add(new DeleteRequest(indexName, user.id))
        }
    }

    @Override
    List<UserDto> update(List<UserDto> userList) {
        userList.each { user ->
            UpdateRequest request = new UpdateRequest(indexName, user.id) // item을 하나씩 가져와서 얘에 대한 정보를 다 쿼리로 작성..?
            //TODO : 업데이트 고쳐야함
            Script script = getUpdateScript(indexName, user)
            request.script(script)

            bulkProcessor.add(request)
        }
    }

    @Override
    List<UserDto> search(SearchOption searchOption) {
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
        List<UserDto> searchResultList = getSearchResult(searchHits)

        return searchResultList
    }

    private List<UserDto> getSearchResult(SearchHits searchHits) {
        List<UserDto> contractList = searchHits?.collect { hit ->
            Map<String, Object> _source = hit.sourceAsMap
            Map<String, Object> _highlighted = hit.highlightFields.collectEntries {
                field, highlightField -> [field, highlightField.fragments?.join()]} as Map<String, Object> // TODO 이부분 확실히 공부

            return new UserDto( // TODO : 이걸 키워드로 할지 텍스트로 할지 고민해보
                    id: _source.get("id"),
                    name: _highlighted.get("name") ?: _source.get("name"),
                    role: _highlighted.get("role") ?: _source.get("role"),
                    phone: _highlighted.get("phone") ?: _source.get("phone"),
                    bid: _highlighted.get("bid") ?: _source.get("bid"),
            )
        } ?: []
        return contractList
    }
}
