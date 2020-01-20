package com.sondahum.mamas.api.controller

import com.sondahum.mamas.elasticsearch.service.EsDao
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping('/search/estate')
class MamasEstateSearchController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName())

    @Autowired
    EsDao esDao

    @GetMapping('register')
    void registerEstate() {
        esDao.save()
    }

    @GetMapping('delete')
    void deleteEstate() {
        esDao.delete()
    }

    @GetMapping('update')
    void updateEstate() {
        esDao.update()
    }

    @GetMapping('search')
    void searchEstate() {
        esDao.search()
    }


}
