package com.sondahum.mamas.elasticsearch.service

import com.sondahum.mamas.elasticsearch.repository.EsDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ContractManageServiceImpl implements ContractManageService{

    @Autowired
    EsDao contractDao
}