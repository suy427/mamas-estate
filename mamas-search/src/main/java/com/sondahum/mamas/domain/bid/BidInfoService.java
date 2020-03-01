package com.sondahum.mamas.domain.bid;

import com.sondahum.mamas.common.error.exception.EntityAlreadyExistException;
import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.domain.contract.Contract;
import com.sondahum.mamas.dto.BidDto;
import com.sondahum.mamas.dto.ContractDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        if (isSameBidExist(bidDto))
            throw new EntityAlreadyExistException(bidDto.getUser());

        Bid bid = bidRepository.save(bidDto.toEntity());

        return new BidDto.DetailResponse(bid);
    }

    @Transactional(readOnly = true)
    public boolean isSameBidExist(BidDto.CreateReq bidDto) {
        Optional<Bid> optionalBid = bidRepository.findByUser_NameAndEstate_NameAndAction(
                bidDto.getUser(),bidDto.getEstate(), Action.findByName(bidDto.getAction()));

        return optionalBid.isPresent();
    }

    public BidDto.DetailResponse updateUserInfo(long id, BidDto.UpdateReq dto) {
        Optional<Bid> optionalContract = bidRepository.findById(id);
        Bid bid = optionalContract.orElseThrow(() -> new NoSuchEntityException(id));

        bid.updateBidInfo(dto);

        return new BidDto.DetailResponse(bid);
    }

    public BidDto.DetailResponse deleteBidInfo(Long id) {
        Optional<Bid> optional = bidRepository.findById(id);
        Bid bid = optional.orElseThrow(() -> new NoSuchEntityException(id)); // todo exception 정리

        bidRepository.deleteById(id);

        return new BidDto.DetailResponse(bid);
    }

}
