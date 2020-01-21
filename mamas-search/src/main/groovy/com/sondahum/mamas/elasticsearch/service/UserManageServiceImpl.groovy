package com.sondahum.mamas.elasticsearch.service

import com.sondahum.mamas.elasticsearch.repository.EstateDao
import com.sondahum.mamas.elasticsearch.repository.UserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ClientManageServiceImpl implements ClientManageService{

    @Autowired
    UserDao userDao
}
