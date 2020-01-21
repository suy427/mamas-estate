package com.sondahum.mamas.elasticsearch.dto

import com.sondahum.mamas.common.model.person.Client

class ClientDto extends EsDto {

    private List<Client> clientList

    @Override
    List<?> getItemList() {
        return clientList
    }
}
