package com.sondahum.mamas.domain.bid;

import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

}
