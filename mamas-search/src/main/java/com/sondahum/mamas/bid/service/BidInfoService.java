package com.sondahum.mamas.bid.service;

import com.sondahum.mamas.bid.dao.BidRepository;
import com.sondahum.mamas.bid.dto.BidDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BidInfoService {

    private static final Logger logger =  LoggerFactory.getLogger(BidInfoService.class);
    private final BidRepository bidRepository;

    public BidInfoService(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    public BidDto.Response createNewUserInfo(BidDto.CreateReq bidDto) {
        return null;
    }

    public BidDto.Response getUserById(long id) {
        return null;
    }

    public BidDto.Response updateUserInfo(long id, BidDto.UpdateReq dto) {
        return null;
    }
}