package com.sondahum.mamas.service

import com.sondahum.mamas.repository.BidRepository
import com.sondahum.mamas.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class HistoryManager implements HistoryManageService {

    private final UserRepository userRepository
    private final BidRepository bidRepository

    HistoryManager(BidRepository bidRepository, UserRepository userRepository) {
        this.userRepository = userRepository
        this.bidRepository = bidRepository
    }
}
