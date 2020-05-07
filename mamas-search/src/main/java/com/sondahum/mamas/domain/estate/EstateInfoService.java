package com.sondahum.mamas.domain.estate;


import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.bid.BidInfoDao;
import com.sondahum.mamas.domain.bid.exception.BidAlreadyExistException;
import com.sondahum.mamas.domain.bid.exception.InvalidActionException;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.domain.contract.Contract;
import com.sondahum.mamas.domain.contract.ContractInfoDao;
import com.sondahum.mamas.domain.contract.exception.ContractAlreadyExistException;
import com.sondahum.mamas.domain.estate.exception.EstateAlreadyExistException;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.domain.user.UserInfoDao;
import com.sondahum.mamas.domain.user.exception.UserAlreadyExistException;
import com.sondahum.mamas.dto.BidDto;
import com.sondahum.mamas.dto.ContractDto;
import com.sondahum.mamas.dto.EstateDto;
import com.sondahum.mamas.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstateInfoService {

    private final EstateInfoDao estateInfoDao;
    private final UserInfoDao userInfoDao;


    public Estate createEstateInfo(EstateDto.CreateReq estateDto) {
        Estate estate;
        User owner;

        try {
            estate = estateInfoDao.createEstateInfo(estateDto);
        } catch (EstateAlreadyExistException e) {
            estate = e.getEstate();
            e.setMessage(estateDto.getName() + " 은(는) 이미 등록된 매물입니다.");
            throw e;
        }

        owner = userInfoDao.findUserByName(estateDto.getOwnerName());
        estate.setOwner(owner);
        owner.addEstate(estate);

        return estate;
    }

    public Estate getEstateById(long id) {
        return estateInfoDao.getEstateById(id);
    }

    public Estate updateEstateInfo(EstateDto.UpdateReq dto) {
        return estateInfoDao.updateEstateInfo(dto);
    }

    public Estate deleteEstateInfo(long id) {
        Estate target = estateInfoDao.deleteEstateInfo(id);
        User user = target.getOwner();

        target.getBidList().removeIf(bid -> bid.getEstate().equals(target));
        target.getContractHistoryList().removeIf(contract -> contract.getEstate().equals(target));
        user.getEstateList().removeIf(estate -> estate.equals(target));

        return target;
    }

}
