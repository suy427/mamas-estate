package com.sondahum.mamas.domain.bid;

import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.domain.bid.exception.BidAlreadyExistException;
import com.sondahum.mamas.domain.bid.exception.InvalidActionException;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.user.User;
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

    public final BidRepository bidRepository;

    // todo 호가는 똑같은걸 여러번 할 수도 있는거 아닐까..? --> 알려만 주자.
    public Bid createBid(Bid bid) {
        return bidRepository.save(bid);
    }

    public Bid getBidById(long id) {
        Optional<Bid> optionalBid = bidRepository.findById(id);
        optionalBid.orElseThrow(() -> new NoSuchEntityException(id));

        return optionalBid.get();
    }

    @Transactional(readOnly = true)
    public Optional<Bid> getDuplicatedBid(String userName, String estateName, Action action) {
        return bidRepository.findByUser_NameAndEstate_NameAndActionAndActiveTrue(userName, estateName, action);
    }

    public Bid updateBidInfo(long id, BidDto.UpdateReq dto) {
        Optional<Bid> optionalContract = bidRepository.findById(id);
        Bid bid = optionalContract.orElseThrow(() -> new NoSuchEntityException(id));

        Estate estate = bid.getEstate();
        User user = bid.getUser();

        bidValidation(estate, user, dto.getAction());

        bid.updateBidInfo(dto);
        return bid;
    }

    private void bidValidation(Estate estate, User user, Action action) {
        if (estate.getOwner().equals(user)) {
            if (action.equals(Action.LEASE) || action.equals(Action.BUY))
                throw new InvalidActionException("자신의 땅은 사거나 임대할  없습니다.");
        } else {
            if (action.equals(Action.SELL) || action.equals(Action.LEND))
                throw new InvalidActionException("본인 명의의 매물만 매도/임대 할 수 있습니다.");
        }
    }

    public Bid softDeleteBid(Long id) {
        Bid bid = bidRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(id));

        bid.setActive(false);
        return bid;
    }

    public void hardDeleteBid(Long id) {
        bidRepository.deleteById(id);
    }

}
