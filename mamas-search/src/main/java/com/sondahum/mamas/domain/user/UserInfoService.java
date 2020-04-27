package com.sondahum.mamas.domain.user;


import com.sondahum.mamas.domain.bid.BidInfoDao;
import com.sondahum.mamas.domain.contract.ContractInfoDao;
import com.sondahum.mamas.domain.estate.EstateInfoDao;
import com.sondahum.mamas.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoDao userInfoDao;
    private final BidInfoDao bidInfoDao;
    private final EstateInfoDao estateInfoDao;
    private final ContractInfoDao contractInfoDao;

    public User createUserInfo(UserDto.CreateReq userDto) {
        return null;
    }

    public User getUserById(long id) {
        return null;
    }

    public User updateUserInfo(long id, UserDto.UpdateReq dto) {
        return null;
    }

    public User deleteUserInfo(long id) {
        return null;
    }
}
