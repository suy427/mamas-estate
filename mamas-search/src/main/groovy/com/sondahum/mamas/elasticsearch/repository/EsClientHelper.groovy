package com.sondahum.mamas.elasticsearch.repository

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
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value

import javax.annotation.PostConstruct

abstract class EsClientHelper {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName())

    protected ObjectMapper objectMapper
    protected BulkProcessor bulkProcessor
    protected RestHighLevelClient client

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
}
