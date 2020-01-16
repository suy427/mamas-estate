package com.sondahum.mamas.elasticsearch.service

import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Value

abstract class RestHighLevelClientHelper {

    @Value('${elasticsearch.host}')
    String hostName

    @Value('${elasticsearch.port}')
    Integer port

    @Value('${elasticsearch.scheme}')
    String scheme

    RestHighLevelClient createConnection() {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost(hostName, port, scheme)
        ));
        return client;
    }
}
