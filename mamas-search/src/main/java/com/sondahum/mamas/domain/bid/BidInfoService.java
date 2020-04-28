package com.sondahum.mamas.domain.bid;


import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.estate.EstateInfoDao;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.domain.user.UserInfoDao;
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
        currentBid = bidDto.toEntity();
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
