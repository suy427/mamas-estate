package com.sondahum.mamas.api.controller

import com.sondahum.mamas.repository.BidRepository
import com.sondahum.mamas.repository.ContractRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping('/contract')
class MamasContractManageController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName())
    private final BidRepository bidRepository
    private final ContractRepository contractRepository

    MamasContractManageController(BidRepository bidRepository, ContractRepository contractRepository) {
        this.bidRepository = bidRepository
        this.contractRepository = contractRepository
    }

    @GetMapping('')
    void index() {

    }

}
