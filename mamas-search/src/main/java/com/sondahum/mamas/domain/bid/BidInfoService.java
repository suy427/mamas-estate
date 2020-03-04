package com.sondahum.mamas.domain.bid;

import com.sondahum.mamas.common.error.exception.EntityAlreadyExistException;
import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.dto.BidDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class BidInfoService {

    private final BidRepository bidRepository;

    @PersistenceContext
    private final EntityManager em;



    public BidDto.DetailResponse createBid(BidDto.CreateReq bidDto) {
        if (isSameBidExist(bidDto))
            throw new EntityAlreadyExistException(bidDto.getUser());

        Bid bid = bidRepository.save(bidDto.toEntity());

        return new BidDto.DetailResponse(bid);
    }

    public BidDto.DetailResponse getBidById(long id) {
        Optional<Bid> optionalBid = bidRepository.findById(id);
        optionalBid.orElseThrow(() -> new NoSuchEntityException(id));

        return new BidDto.DetailResponse(optionalBid.get());
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

        em.flush();
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
