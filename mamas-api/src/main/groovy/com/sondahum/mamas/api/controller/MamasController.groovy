package com.sondahum.mamas.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping('/')
class MamasController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName())

}
