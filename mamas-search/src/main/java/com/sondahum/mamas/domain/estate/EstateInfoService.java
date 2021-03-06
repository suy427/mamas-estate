package com.sondahum.mamas.domain.estate;


import com.sondahum.mamas.domain.estate.exception.EstateAlreadyExistException;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.domain.user.UserInfoDao;
import com.sondahum.mamas.dto.EstateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstateInfoService {

    private final EstateInfoDao estateInfoDao;
    private final UserInfoDao userInfoDao;


    public Estate createEstateInfo(EstateDto.CreateReq estateDto) {
        User owner = userInfoDao.findUserByName(estateDto.getOwnerName());
        Estate returned;

        Estate estate = estateDto.toEntity();

        estate.setOwner(owner);
        owner.addEstate(estate);

        try {
            returned = estateInfoDao.createEstateInfo(estate);
        } catch (EstateAlreadyExistException e) {
            returned = e.getEstate();
            e.setMessage(estateDto.getName() + " 은(는) 이미 등록된 매물입니다.");
            throw e;
        }

        return returned;
    }

    public Estate getEstateById(long id) {
        return estateInfoDao.getEstateById(id);
    }

    public Estate updateEstateInfo(long id, EstateDto.UpdateReq dto) {
        return estateInfoDao.updateEstateInfo(id, dto);
    }

    public Estate deleteEstateSoft(long id) {
        Estate target = estateInfoDao.deleteEstateSoft(id);
        User user = target.getOwner();

        target.getBidList().removeIf(bid -> bid.getEstate().equals(target));
        target.getContractHistoryList().removeIf(contract -> contract.getEstate().equals(target));
        user.getEstateList().removeIf(estate -> estate.equals(target));

        return target;
    }

    public void deleteEstateHard(long id) {
        estateInfoDao.deleteEstateHard(id);
    }

    public List<Estate> getUserEstateList(long id) {
        return userInfoDao.getUserById(id).getEstateList();
    }
}
