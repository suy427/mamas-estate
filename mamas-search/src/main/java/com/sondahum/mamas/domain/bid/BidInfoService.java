package com.sondahum.mamas.domain.bid;


import com.sondahum.mamas.domain.bid.exception.BidAlreadyExistException;
import com.sondahum.mamas.domain.bid.exception.InvalidActionException;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.estate.EstateInfoDao;
import com.sondahum.mamas.domain.estate.exception.EstateAlreadyExistException;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.domain.user.UserInfoDao;
import com.sondahum.mamas.domain.user.exception.UserAlreadyExistException;
import com.sondahum.mamas.dto.BidDto;
import com.sondahum.mamas.dto.EstateDto;
import com.sondahum.mamas.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BidInfoService {

    private final BidInfoDao bidInfoDao;
    private final UserInfoDao userInfoDao;
    private final EstateInfoDao estateInfoDao;

    private Bid currentBid;

    public Bid createBid(BidDto.CreateReq bidDto) {
        User user;
        Estate estate;
        Bid bid;

        try {
            user = userInfoDao.createUserInfo(
                    UserDto.CreateReq.builder()
                            .name(bidDto.getUserName())
                            .build()
            );
        } catch (UserAlreadyExistException ue) {
            user = ue.getUser();
        }

        try {
            estate = estateInfoDao.createEstateInfo(
                    EstateDto.CreateReq.builder()
                            .name(bidDto.getEstateName())
                            .build()
            );
        } catch (EstateAlreadyExistException ee) {
            estate = ee.getEstate();
        }

        if (estate.getOwner().getName().equals(bidDto.getUserName()) && bidDto.getAction().equals(Action.BUY)) {
            throw new InvalidActionException("자신의 땅은 살 수 없습니다.");
        }

        try {
            bid = bidInfoDao.createBid(bidDto);
        } catch (BidAlreadyExistException be) {
            bid = be.getBid();
            be.setMessage("");
        }
        user.getBidList().add(bid);
        estate.getBidList().add(bid);

        return currentBid;
    }

    public User updateUser(UserDto.UpdateReq userDto) {
        User user = currentBid.getUser();
        user.updateUserInfo(userDto);

        return user;
    }

    public Estate updateEstate(EstateDto.UpdateReq estateDto) {
        Estate estate = currentBid.getEstate();
        estate.updateEstateInfo(estateDto);

        return estate;
    }

    public Bid getBidById(long id) {
        currentBid = bidInfoDao.getBidById(id);
        return currentBid;
    }

    public Bid updateBidInfo(BidDto.UpdateReq dto) {
        return bidInfoDao.updateBidInfo(dto);
    }

    public Bid deleteBidInfo(long id) {
        return bidInfoDao.deleteBidInfo(id);
    }
}
