package com.sondahum.mamas.api.controller

import com.sondahum.mamas.repository.EstateRepository
import com.sondahum.mamas.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping('/')
class MamasAssetManageController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName())
    private final UserRepository userRepository
    private final EstateRepository estateRepository

    MamasAssetManageController(UserRepository userRepository, EstateRepository estateRepository) {
        this.userRepository = userRepository
        this.estateRepository = estateRepository
    }


    String userRegister() {
        return ''
    }


}
