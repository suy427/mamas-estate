package com.sondahum.mamas.domain.bid;

import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.domain.bid.exception.BidAlreadyExistException;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.dto.BidDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class BidInfoDao {

    private final BidRepository bidRepository;

    // todo 호가는 똑같은걸 여러번 할 수도 있는거 아닐까..? --> 알려만 주자.
    public Bid createBid(BidDto.CreateReq bidDto) {
        Optional<Bid> duplicatedBid =
                getDuplicatedBid(bidDto.getUserName(), bidDto.getEstateName(), bidDto.getAction());

        if (duplicatedBid.isPresent()) {
            bidRepository.save(duplicatedBid.get());
            throw new BidAlreadyExistException(duplicatedBid.get());
        }

        return bidRepository.save(bidDto.toEntity());
    }

    public Bid getBidById(long id) {
        Optional<Bid> optionalBid = bidRepository.findById(id);
        optionalBid.orElseThrow(() -> new NoSuchEntityException(id));

        return optionalBid.get();
    }

    @Transactional(readOnly = true)
    public Optional<Bid> getDuplicatedBid(String userName, String estateName, Action action) {
        return bidRepository.findByUser_NameAndEstate_NameAndAction_AndActive(
                userName, estateName, action, true);
    }

    public Bid updateBidInfo(BidDto.UpdateReq dto) {
        Optional<Bid> optionalContract = bidRepository.findById(dto.getId());
        Bid bid = optionalContract.orElseThrow(() -> new NoSuchEntityException(dto.getId()));
        bid.updateBidInfo(dto);

        return bid;
    }

    public Bid deleteBidInfo(Long id) {
        Bid bid = bidRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(id));

        bid.setActive(false);
        return bid;
    }

}
