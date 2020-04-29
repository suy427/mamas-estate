package com.sondahum.mamas.domain.estate;


import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.bid.BidInfoDao;
import com.sondahum.mamas.domain.bid.exception.BidAlreadyExistException;
import com.sondahum.mamas.domain.bid.exception.InvalidActionException;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.domain.contract.Contract;
import com.sondahum.mamas.domain.contract.ContractInfoDao;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class EstateInfoService {

    private final EstateInfoDao estateInfoDao;
    private final ContractInfoDao contractDao;
    private final UserInfoDao userInfoDao;
    private final BidInfoDao bidInfoDao;

    private Estate currentEstate;

    public Estate createEstateInfo(EstateDto.CreateReq estateDto) {
        try {
            currentEstate = estateInfoDao.createEstateInfo(estateDto);
        } catch (EstateAlreadyExistException e) {
            currentEstate = e.getEstate();
            e.setMessage(estateDto.getName()+" 은(는) 이미 등록된 매물입니다.");
            throw e;
        }

        return currentEstate;
    }

    public Bid addNewBid(BidDto.CreateReq bidDto) {
        User user;
        Bid bid;

        try {
            user = userInfoDao.createUserInfo(
                    UserDto.CreateReq.builder()
                    .name(bidDto.getUserName())
                    .build()
            );
        } catch (UserAlreadyExistException ue) {
            user = ue.getUser();

            if (currentEstate.getOwner().getName().equals(bidDto.getUserName()) && bidDto.getAction().equals(Action.BUY)) {
                throw new InvalidActionException("자신의 땅은 살 수 없습니다.");
            }

            int amount=0, sell=0, buy=0;
            for (Bid bidHistory : currentEstate.getBidList()) {
                if (bidHistory.getUser().getId().equals(user.getId())) {
                    amount++;
                    if (bidHistory.getAction().equals(Action.BUY))
                        buy++;
                    else sell++;
                }
            }
            ue.setMessage("총 "+amount+"건의 호가 기록이 있는 고객입니다.\n 매수 : "+buy+"매도 : "+sell);
        }



        try {
            bid = bidInfoDao.createBid(bidDto);
        } catch (BidAlreadyExistException be) {
            bid = be.getBid();
            be.setMessage("중복된 정보가 존재합니다. [ "
                    + bid.getEstate()+", "+bid.getAction()+", "+bid.getPriceRange().toString()+" ]");
        }

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
