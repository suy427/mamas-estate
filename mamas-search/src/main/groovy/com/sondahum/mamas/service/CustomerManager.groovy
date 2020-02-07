package com.sondahum.mamas.service

import com.sondahum.mamas.repository.BidRepository
import com.sondahum.mamas.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class CustomerManager implements CustomerManageService {

    private final UserRepository userRepository
    private final BidRepository bidRepository

    CustomerManager(UserRepository userRepository, BidRepository bidRepository) {
        this.userRepository = userRepository
        this.bidRepository = bidRepository
    }
}
