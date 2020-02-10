package com.sondahum.mamas.api.controller

import com.sondahum.mamas.dto.EstateDto
import com.sondahum.mamas.dto.UserDto
import com.sondahum.mamas.repository.EstateRepository
import com.sondahum.mamas.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping('/asset')
class MamasAssetManageController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName())


}
