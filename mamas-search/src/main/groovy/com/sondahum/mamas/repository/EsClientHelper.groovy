package com.sondahum.mamas.repository

import com.sondahum.mamas.elasticsearch.dto.EsDto
import org.apache.http.HttpHost
import org.codehaus.jackson.map.ObjectMapper
import org.elasticsearch.action.bulk.BackoffPolicy
import org.elasticsearch.action.bulk.BulkItemResponse
import org.elasticsearch.action.bulk.BulkProcessor
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.bulk.BulkResponse
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.unit.TimeValue
import org.elasticsearch.script.Script
import org.elasticsearch.script.ScriptType
import org.elasticsearch.search.SearchHits
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value

import javax.annotation.PostConstruct
import static com.sondahum.mamas.common.util.JUtils.objToMap

abstract class EsClientHelper {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName())

    protected ObjectMapper objectMapper
    protected BulkProcessor bulkProcessor
    protected RestHighLevelClient esClient

    @Value('${elasticsearch.host}')
    String hostName

    @Value('${elasticsearch.port}')
    Integer port

    @Value('${elasticsearch.scheme}')
    String scheme


    RestHighLevelClient createConnection() {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost(hostName, port, scheme)
        ))
        return client
    }

    @PostConstruct // 얘는 스프링에서 was각 띄워질때 실행된다는데 얘는 abstract
    void init() {
        this.esClient = createConnection()
        this.objectMapper = new ObjectMapper()
        this.bulkProcessor = BulkProcessor.builder(
                { bulkRequest, listener ->
                    esClient.bulkAsync(bulkRequest, RequestOptions.DEFAULT, listener)
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
                String resultLog = "< RESPONSE: ${count} FAIL:${failureList.size()} >"
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

    Script getUpdateScript(String indexName, EsDto dto) {

        Map<String, Object> params = objToMap(dto)
        params.put('modifiedDate', new Date())

        Script script = new Script(
                ScriptType.STORED,
                'painless',
                "mamas-script-update-${indexName}",
                params)

        return script
    }

    List<EsDto> getSearchResult(SearchHits searchHits) {
        List<EsDto> dtoList = searchHits?.collect { hit ->
            Map<String, Object> _source = hit.sourceAsMap
            Map<String, Object> _highlighted = hit.highlightFields.collectEntries {
                field, highlightField -> [field, highlightField.fragments?.join()]} as Map<String, Object> // TODO 이부분 확실히 공부

            // 팩토리 메소드 디자인 패턴 적용했는데 제대로 된걸까...? 이상하게 느껴진다...
            EsDto dto = matchDto(_source, _highlighted)
            return dto

        } ?: []
        return dtoList
    }

    protected abstract EsDto matchDto(Map<String, Object> _source, Map<String, Object> _highlighted)

}
