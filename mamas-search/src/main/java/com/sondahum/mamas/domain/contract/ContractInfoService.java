package com.sondahum.mamas.domain.contract;


import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.estate.EstateInfoDao;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.domain.user.UserInfoDao;
import com.sondahum.mamas.dto.ContractDto;
import com.sondahum.mamas.dto.EstateDto;
import com.sondahum.mamas.dto.UserDto;
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

    private Contract currentContract;

    public Contract createContractInfo(ContractDto.CreateReq contractDto) {
        currentContract = contractInfoDao.createContractInfo(contractDto);
        return currentContract;
    }

    public User updateSeller(UserDto.UpdateReq userDto) {
        User seller = currentContract.getSeller();
        seller.updateUserInfo(userDto);

        return seller;
    }

    public User updateBuyer(UserDto.UpdateReq userDto) {
        User buyer = currentContract.getBuyer();
        buyer.updateUserInfo(userDto);

        return buyer;
    }

    public Estate updateEstate(EstateDto.UpdateReq estateDto) {
        Estate estate = currentContract.getEstate();
        estate.updateEstateInfo(estateDto);

        return estate;
    }

    public Contract getContractById(long id) {
        currentContract = contractInfoDao.getContractById(id);
        return currentContract;
    }

    public Contract updateContractInfo(ContractDto.UpdateReq dto) {
        return contractInfoDao.updateContractInfo(dto);
    }

    public Contract deleteContractInfo(long id) {
        return contractInfoDao.deleteContractInfo(id);
    }
}
