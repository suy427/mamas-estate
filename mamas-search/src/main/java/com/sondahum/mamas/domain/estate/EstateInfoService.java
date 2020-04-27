package com.sondahum.mamas.domain.estate;


import com.sondahum.mamas.domain.user.UserInfoDao;
import com.sondahum.mamas.dto.EstateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstateInfoService {

    private final EstateInfoDao estateInfoDao;
    private final UserInfoDao userInfoDao;

    public Estate createEstateInfo(EstateDto.CreateReq estateDto) {
        return null;
    }

    public Estate getEstateById(long id) {
        return null;
    }

    public Estate updateEstateInfo(long id, EstateDto.UpdateReq dto) {
        return null;
    }

    public Estate deleteEstateInfo(long id) {
        return null;
    }
}
