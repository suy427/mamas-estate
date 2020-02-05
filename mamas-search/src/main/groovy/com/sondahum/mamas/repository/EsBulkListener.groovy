package com.sondahum.mamas.elasticsearch.repository

import org.elasticsearch.action.bulk.BulkItemResponse
import org.elasticsearch.action.bulk.BulkProcessor
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.bulk.BulkResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class EsBulkListener implements BulkProcessor.Listener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName())

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

}
