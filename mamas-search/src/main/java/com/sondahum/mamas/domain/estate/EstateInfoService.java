package com.sondahum.mamas.domain.estate;


import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.bid.BidInfoDao;
import com.sondahum.mamas.domain.contract.Contract;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.domain.user.UserInfoDao;
import com.sondahum.mamas.dto.BidDto;
import com.sondahum.mamas.dto.ContractDto;
import com.sondahum.mamas.dto.EstateDto;
import com.sondahum.mamas.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstateInfoService {

    private final EstateInfoDao estateInfoDao;
    private final ContractDto contractDto;
    private final UserInfoDao userInfoDao;
    private final BidInfoDao bidInfoDao;

    private Estate currentEstate;

    public Estate createEstateInfo(EstateDto.CreateReq estateDto) {
        currentEstate = estateInfoDao.createEstateInfo(estateDto);
        return currentEstate;
    }

    public Bid addNewBid(BidDto.CreateReq bidDto) {
        Bid bid = bidDto.toEntity();
        currentEstate.getBidList().add(bid);

        return bid;
    }

    // todo 현재 collection을 탐색해서 바꾸는중... cascade가 제대로 동작하는지 확인 필요
    public Bid updateBid(BidDto.UpdateReq bidDto) {
        Bid bid = currentEstate.getBidList().stream()
                .filter(element -> element.getId().equals(bidDto.getId()))
                .findFirst().orElseThrow(() -> new NoSuchEntityException(bidDto.getId()));

        bid.updateBidInfo(bidDto);

        return bid;
    }

    public void deleteBid(Long id) {
        currentEstate.getBidList().removeIf(bid -> bid.getId().equals(id));
    }

    public User updateOwner(UserDto.UpdateReq userDto) {
        User user = currentEstate.getOwner();
        user.updateUserInfo(userDto);

        return user;
    }

    public Contract addNewContractHistory(ContractDto.CreateReq contractDto) {
        Contract contract = contractDto.toEntity();
        currentEstate.getContractHistoryList().add(contract);

        return contract;
    }

    public Contract updateContractHistory(ContractDto.UpdateReq contractDto) {
        Contract contract = currentEstate.getContractHistoryList().stream()
                .filter(element -> element.getId().equals(contractDto.getId()))
                .findFirst().orElseThrow(() -> new NoSuchEntityException(contractDto.getId()));

        contract.updateContractInfo(contractDto);

        return contract;
    }

    public void deleteContractHistory(Long id) {
        currentEstate.getContractHistoryList().removeIf(contract -> contract.getId().equals(id));
    }


    public Estate getEstateById(long id) {
        currentEstate = estateInfoDao.getEstateById(id);
        return currentEstate;
    }

    public Estate updateEstateInfo(EstateDto.UpdateReq dto) {
        return estateInfoDao.updateEstateInfo(dto);
    }

    public Estate deleteEstateInfo(long id) {
        return estateInfoDao.deleteEstateInfo(id);
    }
}
