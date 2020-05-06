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
    private final ContractInfoDao contractDao;
    private final UserInfoDao userInfoDao;
    private final BidInfoDao bidInfoDao;

    private Estate currentEstate;

    public Estate createEstateInfo(EstateDto.CreateReq estateDto) {
        try {
            currentEstate = estateInfoDao.createEstateInfo(estateDto);
        } catch (EstateAlreadyExistException e) {
            currentEstate = e.getEstate();
            e.setMessage(estateDto.getName() + " 은(는) 이미 등록된 매물입니다.");
            throw e;
        }

        return currentEstate;
    }

    @Transactional(rollbackFor = Exception.class)
    public Bid addNewBid(UserDto.CreateReq userDto, BidDto.CreateReq bidDto) {
        User user = specifyUser(userDto);

        if (currentEstate.getOwner().equals(user)) {
            if (bidDto.getAction().equals(Action.LEASE) || bidDto.getAction().equals(Action.BUY))
            throw new InvalidActionException("자신의 땅은 사거나 임대할  없습니다.");
        }

        Bid bid = specifyBid(bidDto);
        bid.setUser(user);
        bid.setEstate(currentEstate);

        user.addBidHistory(bid);

        currentEstate.addBidHistory(bid);

        return bid;
    }

    // 땅 고정. (유저, 가격)
    @Transactional(rollbackFor = Exception.class) //
    public Bid updateBid(BidDto.UpdateReq bidDto) {
        Bid target = currentEstate.getBidList().stream()
                .filter(bid -> bid.getId().equals(bidDto.getId()))
                .findFirst().orElseThrow(() -> new NoSuchEntityException(bidDto.getId()));

        if (!target.getUser().getName().equals(bidDto.getUser())) { // 사람이 업데이트되는 경우
            target.getUser().getBidList().removeIf(bid -> bid.equals(target));
            User newUser = specifyUser(UserDto.CreateReq.builder().name(bidDto.getUser()).build());
            newUser.addBidHistory(target);
            target.setUser(newUser);
        }

        target.updateBidInfo(bidDto);
        return target;
    }

    public List<Bid> deleteBid(Long id) {
        bidInfoDao.deleteBidInfo(id);
        return currentEstate.getBidList();
    }

    @Transactional(rollbackFor = Exception.class) //
    public User updateOwner(UserDto.UpdateReq userDto) {
        User user = currentEstate.getOwner();
        user.getEstateList().removeIf(estate -> estate.equals(currentEstate));

        User newOwner = specifyUser(UserDto.CreateReq.builder().name(userDto.getName()).build());
        currentEstate.setOwner(newOwner);

        return currentEstate.getOwner();
    }

    @Transactional(rollbackFor = Exception.class) //
    public Contract addNewContractHistory(EstateDto.CreateReq estateDto, ContractDto.CreateReq contractDto) {
        User seller = userInfoDao.createUserInfo(UserDto.CreateReq.builder().name(contractDto.getSeller()).build());
        User buyer = userInfoDao.createUserInfo(UserDto.CreateReq.builder().name(contractDto.getBuyer()).build());
        Contract contract = specifyContract(contractDto);

        if (currentEstate.getOwner().equals(buyer))


        currentEstate.getContractHistoryList().add(contract);


        return contract;
    }

    public Contract updateContractHistory(ContractDto.UpdateReq contractDto) {
        Contract target = currentEstate.getContractHistoryList().stream()
                .filter(element -> element.getId().equals(contractDto.getId()))
                .findFirst().orElseThrow(() -> new NoSuchEntityException(contractDto.getId()));


        if (!target.getBuyer().getName().equals(contractDto.getBuyer())) {
            target.getBuyer().getContractList().removeIf(contract -> contract.equals(target));

            User newBuyer = specifyUser(UserDto.CreateReq.builder().name(contractDto.getBuyer()).build());
            newBuyer.addContractHistory(target);
            target.setBuyer(newBuyer);
        }

        if (!target.getSeller().getName().equals(contractDto.getSeller())) {
            target.getSeller().getContractList().removeIf(contract -> contract.equals(target));

            User newSeller = specifyUser(UserDto.CreateReq.builder().name(contractDto.getSeller()).build());
            newSeller.addContractHistory(target);
            target.setSeller(newSeller);
        }

        target.updateContractInfo(contractDto);
        return target;
    }

    public List<Contract> deleteContractHistory(Long id) {
        contractDao.deleteContractInfo(id);
        return currentEstate.getContractHistoryList();
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

    private User specifyUser(UserDto.CreateReq userDto) {
        User user;

        try {
            user = userInfoDao.createUserInfo(userDto);
        } catch (UserAlreadyExistException e) {
            user = e.getUser();
            e.setMessage(userDto.getName() + "님의 정보가 이미 존재합니다.");
        }
        return user;
    }

    private Bid specifyBid(BidDto.CreateReq bidDto) {
        Bid bid;

        try {
            bid = bidInfoDao.createBid(bidDto);
        } catch (BidAlreadyExistException e) {
            bid = e.getBid();
            e.setMessage("중복된 정보가 존재합니다. [ "
                    + bid.getEstate() + ", " + bid.getAction() + ", " + bid.getPriceRange().toString() + " ]");
        }
        return bid;
    }

    private Contract specifyContract(ContractDto.CreateReq contractDto) {
        Contract contract;

        try {
            contract = contractDao.createContractInfo(contractDto);
        } catch (ContractAlreadyExistException e) {
            contract = e.getContract();
            e.setMessage("중복된 정보가 존재합니다.\n"+contract.toString());
        }
        return contract;
    }

}
