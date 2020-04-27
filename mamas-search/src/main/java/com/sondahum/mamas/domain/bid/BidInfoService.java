package com.sondahum.mamas.domain.bid;


import com.sondahum.mamas.domain.estate.EstateInfoDao;
import com.sondahum.mamas.domain.user.UserInfoDao;
import com.sondahum.mamas.dto.BidDto;
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

    public Bid createBid(BidDto.CreateReq bidDto) {
        return null;
    }

    public Bid getBidById(long id) {
        return null;
    }

    public Bid updateBidInfo(long id, BidDto.UpdateReq dto) {
        return null;
    }

    public Bid deleteBidInfo(long id) {
        return null;
    }
}
