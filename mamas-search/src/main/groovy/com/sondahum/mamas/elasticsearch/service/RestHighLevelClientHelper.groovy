package com.sondahum.mamas.elasticsearch.service

import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient

abstract class RestHighLevelClientHelper {
    private static final hostName = '127.0.0.1'
    private static final port = '9200'
    private static final scheme = 'http'

    RestHighLevelClient createConnection() {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost(hostName, port, scheme)
        ));
        return client;
    }
}
