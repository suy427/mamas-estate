package com.sondahum.mamas.domain.bid;


import com.sondahum.mamas.domain.bid.exception.InvalidActionException;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.domain.contract.Contract;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.estate.EstateInfoDao;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.domain.user.UserInfoDao;
import com.sondahum.mamas.dto.BidDto;
import com.sondahum.mamas.dto.EstateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        Bid bid = bidInfoDao.createBid(bidDto);
        bid.setUser(user);
        bid.setEstate(estate);

        user.addBidHistory(bid);
        estate.addBidHistory(bid);

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

    @Transactional(rollbackFor = Exception.class)
    public Bid addNewBid(BidDto.CreateReq bidDto) { // 땅은 현재땅(등록돼야 bid추가가능. owner는 팝업으로 먼저했다.)
        Estate estate = estateInfoDao.findEstateByName(bidDto.getEstateName());
        User user = userInfoDao.findUserByName(bidDto.getUserName());

        bidValidation(estate, user, bidDto.getAction());

        Bid bid = bidInfoDao.createBid(bidDto);
        bid.setUser(user);
        bid.setEstate(estate);

        user.addBidHistory(bid);

        estate.addBidHistory(bid);

        return bid;
    }

    // 땅 고정. (유저, 가격)
    @Transactional(rollbackFor = Exception.class) //
    public Bid updateBid(BidDto.UpdateReq bidDto) {
        Bid originalBid = bidInfoDao.getBidById(bidDto.getId());
        Estate estate = originalBid.getEstate();
        User user = originalBid.getUser();

        bidValidation(estate, user, bidDto.getAction());

        // 연관관계는 못바꾸고 내용만 바꿀 수 있음.

        return originalBid.updateBidInfo(bidDto);
    }

    public Bid revertBid(Long id) {
        Bid target = bidInfoDao.deleteBidInfo(id);

        target.getEstate().getBidList().removeIf(bid -> bid.equals(target));
        target.getUser().getBidList().removeIf(bid -> bid.equals(target));

        return target;
    }

    public Bid getBidById(long id) {
        return bidInfoDao.getBidById(id);
    }

    public Bid updateBidInfo(BidDto.UpdateReq dto) {
        return bidInfoDao.updateBidInfo(dto);
    }

    public Bid deleteBidInfo(long id) {
        return bidInfoDao.deleteBidInfo(id);
    }
}
