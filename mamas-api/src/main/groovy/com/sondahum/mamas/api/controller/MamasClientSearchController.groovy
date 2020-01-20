package com.sondahum.mamas.api.controller

import com.sondahum.mamas.elasticsearch.service.EsDao
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping('/search/client')
class MamasClientSearchController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName())

    @Autowired
    EsDao esDao


    @GetMapping('register')
    void registerClient() {
        esDao.save()
    }

    @GetMapping('delete')
    void deleteClient() {
        esDao.delete()
    }

    @GetMapping('update')
    void updateClient() {
        esDao.update()
    }

    @GetMapping('search')
    void searchClient() {
        esDao.search()
    }

}
