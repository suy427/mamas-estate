package com.sondahum.mamas.elasticsearch.service

import com.sondahum.mamas.elasticsearch.model.EsDto
import com.sondahum.mamas.elasticsearch.model.SearchOption
import org.elasticsearch.action.bulk.BackoffPolicy
import org.elasticsearch.action.bulk.BulkProcessor
import org.elasticsearch.action.bulk.BulkRequest
import org.codehaus.jackson.map.ObjectMapper
import org.elasticsearch.action.bulk.BulkResponse
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.unit.TimeValue
import org.elasticsearch.common.xcontent.XContentType
import org.junit.platform.commons.logging.LoggerFactory

import javax.annotation.PostConstruct
import java.util.logging.Logger

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

            }

            @Override
            void afterBulk(long executionId, BulkRequest request, BulkResponse response) {

            }

            @Override
            void afterBulk(long executionId, BulkRequest request, Throwable failure) {

            }
        }).setBulkActions(10000) // how many requests at once
                .setConcurrentRequests(1)
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build()
    }

    @Override
    void save(EsDto esDto) {
        byte[] byteContents = objectMapper.writeValueAsBytes(esDto)
        bulkProcessor.add(new IndexRequest(esDto.indexName).source(byteContents, XContentType.JSON))
    }

    @Override
    void delete(EsDto esDto) {

    }

    @Override
    void update(EsDto esDto) {

    }

    @Override
    String search(SearchOption searchOption) {
        return null
    }
}
