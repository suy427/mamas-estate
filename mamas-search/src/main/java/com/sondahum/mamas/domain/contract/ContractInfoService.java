package com.sondahum.mamas.domain.contract;


import com.sondahum.mamas.domain.estate.EstateInfoDao;
import com.sondahum.mamas.domain.user.UserInfoDao;
import com.sondahum.mamas.dto.ContractDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContractInfoService {

    private final UserInfoDao userInfoDao;
    private final EstateInfoDao estateInfoDao;
    private final ContractInfoDao contractInfoDao;

    public Contract createContractInfo(ContractDto.CreateReq userDto) {
        return null;
    }

    public Contract getContractById(long id) {
        return null;
    }

    public Contract updateContractInfo(long id, ContractDto.UpdateReq dto) {
        return null;
    }

    public Contract deleteContractInfo(long id) {
        return null;
    }
}
