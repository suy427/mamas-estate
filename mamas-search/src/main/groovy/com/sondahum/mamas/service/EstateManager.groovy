package com.sondahum.mamas.service

import com.sondahum.mamas.repository.EstateRepository
import com.sondahum.mamas.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class EstateManager implements EstateManageService {

    private final EstateRepository estateRepository
    private final UserRepository userRepository

    EstateManager(EstateRepository estateRepository, UserRepository userRepository) {
        this.estateRepository = estateRepository
        this.userRepository = userRepository
    }
}
