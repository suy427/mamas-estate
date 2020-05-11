package com.sondahum.mamas.domain.bid;


import com.sondahum.mamas.domain.bid.exception.BidAlreadyExistException;
import com.sondahum.mamas.domain.bid.exception.InvalidActionException;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.estate.EstateInfoDao;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.domain.user.UserInfoDao;
import com.sondahum.mamas.dto.BidDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BidInfoService {

    private final BidInfoDao bidInfoDao;
    private final UserInfoDao userInfoDao;
    private final EstateInfoDao estateInfoDao;


    @Transactional(rollbackFor = Exception.class)
    public Bid createBid(BidDto.CreateReq bidDto) {
       User user = userInfoDao.findUserByName(bidDto.getUserName());
       Estate estate = estateInfoDao.findEstateByName(bidDto.getEstateName());

        if (estate.getOwner().equals(user) && bidDto.getAction().equals(Action.BUY)) {
            throw new InvalidActionException("자신의 땅은 살 수 없습니다.");
        }

        Bid bid;

        Optional<Bid> duplicatedBid =
                bidInfoDao.getDuplicatedBid(bidDto.getUserName(), bidDto.getEstateName(), bidDto.getAction());

        if (duplicatedBid.isPresent()) {
            return duplicatedBid.get();
        } else
            bid = bidDto.toEntity();

        bid.setUser(user);
        bid.setEstate(estate);

        user.addBidHistory(bid);
        estate.addBidHistory(bid);

        return bid;
    }

    // 땅 고정. (유저, 가격)
    // bid는 내용이 바뀌면 체크해줘야할게 있다.
    @Transactional(rollbackFor = Exception.class)
    public Bid updateBid(long id, BidDto.UpdateReq bidDto) {
        return bidInfoDao.updateBidInfo(id, bidDto);
    }

    public Bid revertBid(Long id) {
        Bid target = bidInfoDao.softDeleteBid(id);

        target.getEstate().getBidList().removeIf(bid -> bid.equals(target));
        target.getUser().getBidList().removeIf(bid -> bid.equals(target));

        return target;
    }

    public Bid getBidById(long id) {
        return bidInfoDao.getBidById(id);
    }

    public void deleteBidInfo(long id) {
        bidInfoDao.hardDeleteBid(id);
    }

    public List<Bid> getUserBidList(long userId) {
        return userInfoDao.getUserById(userId).getBidList();
    }
}
