package com.sondahum.mamas.bid;

import com.sondahum.mamas.bid.dao.BidRepository;
import com.sondahum.mamas.bid.domain.Bid;
import com.sondahum.mamas.bid.dto.BidDto;
import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.user.domain.User;
import com.sondahum.mamas.user.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BidInfoService {

    private static final Logger logger =  LoggerFactory.getLogger(BidInfoService.class);
    private final BidRepository bidRepository;

    public BidInfoService(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }


    public BidDto.DetailResponse createBid(BidDto.CreateReq bidDto) {
        return null;
    }

    public BidDto.DetailResponse updateUserInfo(long id, BidDto.UpdateReq dto) {
        return null;
    }

    public BidDto.DetailResponse deleteBidInfo(Long id) {
        Optional<Bid> optional = bidRepository.findById(id);
        Bid bid = optional.orElseThrow(() -> new NoSuchEntityException(id)); // todo exception 정리

        bidRepository.deleteById(id);

        return new BidDto.DetailResponse(bid);
    }

    @Transactional(readOnly = true)
    public Page<Bid> searchBid(final BidDto.SearchReq query, final Pageable pageable) {
        Page<Bid> searchResult;

        if (query == null) {
            searchResult = bidRepository.findAll(pageable);
        } else {
            searchResult = null;
        }

        return searchResult;
    }
}
