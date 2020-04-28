package com.sondahum.mamas.domain.user;


import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.bid.BidInfoDao;
import com.sondahum.mamas.domain.contract.Contract;
import com.sondahum.mamas.domain.contract.ContractInfoDao;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.estate.EstateInfoDao;
import com.sondahum.mamas.dto.BidDto;
import com.sondahum.mamas.dto.ContractDto;
import com.sondahum.mamas.dto.EstateDto;
import com.sondahum.mamas.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoDao userInfoDao;
    private final BidInfoDao bidInfoDao;
    private final EstateInfoDao estateInfoDao;
    private final ContractInfoDao contractInfoDao;

    private User currentUser;

    public User createUserInfo(UserDto.CreateReq userDto) {
        currentUser = userInfoDao.createUserInfo(userDto); // 여기서 repo에 넣어줌
        return currentUser;
    }

    public Estate addNewEstate(EstateDto.CreateReq estateDto) {
        Estate estate = estateDto.toEntity();
        currentUser.getEstateList().add(estate);

        return estate;
    }

    public Estate updateEstate(EstateDto.UpdateReq estateDto) {
        Estate estate = currentUser.getEstateList().stream()
                .filter(element -> element.getId().equals(estateDto.getId()))
                .findFirst().orElseThrow(() -> new NoSuchEntityException(estateDto.getId()));

        estate.updateEstateInfo(estateDto);

        return estate;
    }

    public void deleteEstate(Long id) {
        currentUser.getEstateList().removeIf(estate -> estate.getId().equals(id));
    }

    public Bid addNewBid(BidDto.CreateReq bidDto) {
        Bid bid = bidDto.toEntity();
        currentUser.getBidList().add(bid);

        return bid;
    }

    public Bid updateBid(BidDto.UpdateReq bidDto) {
        Bid bid = currentUser.getBidList().stream()
                .filter(element -> element.getId().equals(bidDto.getId()))
                .findFirst().orElseThrow(() -> new NoSuchEntityException(bidDto.getId()));

        bid.updateBidInfo(bidDto);

        return bid;
    }

    public void deleteBid(Long id) {
        currentUser.getBidList().removeIf(bid -> bid.getId().equals(id));
    }

    public Contract addNewContractHistory(ContractDto.CreateReq contractDto) {
        Contract contract = contractDto.toEntity();
        currentUser.getContractList().add(contract);

        return contract;
    }

    public Contract updateContract(ContractDto.UpdateReq contractDto) {
        Contract contract = currentUser.getContractList().stream()
                .filter(element -> element.getId().equals(contractDto.getId()))
                .findFirst().orElseThrow(() -> new NoSuchEntityException(contractDto.getId()));

        contract.updateContractInfo(contractDto);

        return contract;
    }

    public void deleteContract(Long id) {
        currentUser.getContractList().removeIf(contract -> contract.getId().equals(id));
    }

    public List<EstateDto.SimpleResponse> getOwningEstateList() {
        return currentUser.getEstateList().stream()
                .map(EstateDto.SimpleResponse::new)
                .collect(Collectors.toList());
    }

    public List<BidDto.DetailResponse> getBiddingList() {
        return currentUser.getBidList().stream()
                .map(BidDto.DetailResponse::new)
                .collect(Collectors.toList());
    }

    public List<ContractDto.DetailResponse> getContractHistoryList() {
        return currentUser.getContractList().stream()
                .map(ContractDto.DetailResponse::new)
                .collect(Collectors.toList());
    }

    public User getUserById(long id) {
        currentUser = userInfoDao.getUserById(id);
        return currentUser;
    }

    public User updateUserInfo(UserDto.UpdateReq dto) {
        return userInfoDao.updateUserInfo(dto);
    }

    public User deleteUserInfo(long id) {
        return userInfoDao.deleteUserInfo(id);
    }
}
