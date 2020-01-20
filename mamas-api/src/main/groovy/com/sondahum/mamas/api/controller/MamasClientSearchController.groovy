package com.sondahum.mamas.api.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping('/search/client')
class MamasClientSearchController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName())

    @GetMapping('register')
    void registerClient() {

    }

    @GetMapping('delete')
    void deleteClient() {

    }

    @GetMapping('update')
    void updateClient() {

    }

    @GetMapping('search')
    void searchClient() {

    }

}
