package com.sondahum.mamas.domain.contract;


import com.sondahum.mamas.domain.bid.exception.InvalidActionException;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.estate.EstateInfoDao;
import com.sondahum.mamas.domain.estate.model.EstateStatus;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.domain.user.UserInfoDao;
import com.sondahum.mamas.dto.ContractDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContractInfoService {

    private final UserInfoDao userInfoDao;
    private final EstateInfoDao estateInfoDao;
    private final ContractInfoDao contractInfoDao;

    public Contract createContractInfo(ContractDto.CreateReq contractDto) { // 맨 마지막
        User seller = userInfoDao.findUserByName(contractDto.getSeller());
        User buyer = userInfoDao.findUserByName(contractDto.getBuyer());;
        Estate estate = estateInfoDao.findEstateByName(contractDto.getEstate());

        if (seller.equals(buyer)) {
            throw new InvalidActionException("자가 계약은 불가능합니다.");
        }

        if (!estate.getOwner().equals(seller)) {
            throw new InvalidActionException("매도자 소유의 매물이 아닙니다. [ " +estate.getOwner().getName()+" ]");
        }

        Contract contract = contractDto.toEntity();

        contract.setSeller(seller);
        contract.setBuyer(buyer);
        contract.setEstate(estate);

        buyer.addContractHistory(contract);
        seller.addContractHistory(contract);
        estate.addContractHistory(contract);

        seller.getEstateList().removeIf(property -> property.getId().equals(estate.getId()));
        buyer.addEstate(estate);

        return contractInfoDao.createContractInfo(contract);
    }

    public Contract getContractById(long id) {
        return contractInfoDao.getContractById(id);
    }

    public Contract revertContract(long id) {
        Contract target = contractInfoDao.softDeleteContract(id);

        target.getBuyer().getEstateList() // 산거 무효
                .removeIf(estate -> estate.equals(target.getEstate()));
        target.getSeller().addEstate(target.getEstate()); // 판거 무효
        target.getBuyer().getContractList() // 계약 무효
                .removeIf(contract -> contract.equals(target));
        target.getSeller().getContractList() // 계약 무효
                .removeIf(contract -> contract.equals(target));
        target.getEstate().getContractHistoryList() // 계약 무효
                .removeIf(contract -> contract.getId().equals(target.getId()));

        target.getEstate().setStatus(EstateStatus.ONSALE); // 땅 상태 판매중.

        return target;
    }

    public void deleteContract(long id) {
        contractInfoDao.hardDeleteContract(id);
    }

    // 계약 내용은 바뀔 수 있다 + estate의 명칭까지..! --> 계약 내용만 바뀌도록..!
    public Contract updateContractInfo(long id, ContractDto.UpdateReq contractDto) {
        return contractInfoDao.updateContractInfo(id, contractDto);
    }

    public List<Contract> getUserContractList(long userId) {
        return userInfoDao.getUserById(userId).getContractList();
    }
}
