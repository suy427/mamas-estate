package com.sondahum.mamas.elasticsearch.repository

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
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value

import javax.annotation.PostConstruct

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

    Script getUpdateScript(String indexName, EsDto dto) { // 여기는 엔티티가 들어와야함 에다 수정할 내용을 담아서 고대로 바꿈

        Map<String, Object> params = dto.toMap() // toMap 필요함..!!!
        params.put('modifiedDate', new Date())

        Script script = new Script(
                ScriptType.STORED,
                'painless',
                "mamas-script-update-${indexName}",
                params)

        return script
    }
}
