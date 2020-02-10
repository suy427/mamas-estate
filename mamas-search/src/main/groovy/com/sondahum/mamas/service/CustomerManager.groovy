package com.sondahum.mamas.service

import com.sondahum.mamas.domain.Bid
import com.sondahum.mamas.domain.User
import com.sondahum.mamas.dto.BidDto
import com.sondahum.mamas.dto.UserDto
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

    List<UserDto> getUserList() {
        List<User> userEntityList = userRepository.findAll()
        List<UserDto> userDtoList = userEntityList.collect { userEntity ->
            userEntity.toDto()
        }
        return userDtoList
    }

    String createNewUser(UserDto userDto) {
        User userEntity = userDto.toEntity()
        userRepository.save(userEntity)
    }

    String createNewBid(BidDto bidDto) { // with user info upsert.
        Bid bidEntity = bidDto.toEntity()
        bidRepository.save(bidEntity)
    }




}
