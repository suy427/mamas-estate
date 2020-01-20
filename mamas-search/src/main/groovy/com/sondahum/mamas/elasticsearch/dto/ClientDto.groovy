package com.sondahum.mamas.elasticsearch.dto

import com.sondahum.mamas.common.model.person.Client

class ClientDto implements EsDto {

    private Double totalCount
    private List<Client> clientList
}
