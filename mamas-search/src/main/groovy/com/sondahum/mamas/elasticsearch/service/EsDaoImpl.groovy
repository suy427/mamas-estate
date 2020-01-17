package com.sondahum.mamas.elasticsearch.service

import com.sondahum.mamas.elasticsearch.model.EsDto
import com.sondahum.mamas.elasticsearch.model.SearchOption
import org.elasticsearch.action.bulk.BackoffPolicy
import org.elasticsearch.action.bulk.BulkItemResponse
import org.elasticsearch.action.bulk.BulkProcessor
import org.elasticsearch.action.bulk.BulkRequest
import org.codehaus.jackson.map.ObjectMapper
import org.elasticsearch.action.bulk.BulkResponse
import org.elasticsearch.action.delete.DeleteRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.action.search.SearchType
import org.elasticsearch.action.update.UpdateRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.unit.TimeValue
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.script.ScriptType
import org.elasticsearch.script.mustache.SearchTemplateRequest
import org.elasticsearch.script.mustache.SearchTemplateResponse
import org.elasticsearch.search.SearchHits
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class EsDaoImpl extends RestHighLevelClientHelper implements EsDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName())

    private ObjectMapper objectMapper
    private BulkProcessor bulkProcessor
    private RestHighLevelClient client

    @PostConstruct
    void init() {
        this.client = createConnection()
        this.objectMapper = new ObjectMapper()
        this.bulkProcessor = BulkProcessor.builder(
                { bulkRequest, listener ->
                    client.bulkAsync(bulkRequest, RequestOptions.DEFAULT, listener)
                }, new BulkProcessor.Listener() {
            @Override
            void beforeBulk(long executionId, BulkRequest request) {
                logger.info "[${executionId}] before Bulk - going to bulk of ${request.numberOfActions()} request"
            }

            @Override
            void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                List<BulkItemResponse> responseList = response.getItems()
                List<BulkItemResponse.Failure> failureList = []

                responseList.each { bulkResponse ->
                    failureList.add(bulkResponse.getFailure())
                }

                Long count = responseList.size() ?: -1
                String resultLog = "< Check INDEX: test1> SIZE: ${count} FAIL:${failureList.size()} >"
                logger.info "[${executionId}] ElasticSearch ${resultLog}"
            }

            @Override
            void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                logger.warn("error while executing bulk", failure);
            }
        }).setBulkActions(10000) // how many requests at once
                .setConcurrentRequests(1)
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build()
    }

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
