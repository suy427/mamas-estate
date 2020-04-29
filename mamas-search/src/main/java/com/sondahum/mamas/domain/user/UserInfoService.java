package com.sondahum.mamas.domain.user;


import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.bid.BidInfoDao;
import com.sondahum.mamas.domain.bid.exception.BidAlreadyExistException;
import com.sondahum.mamas.domain.bid.exception.InvalidActionException;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.domain.contract.Contract;
import com.sondahum.mamas.domain.contract.ContractInfoDao;
import com.sondahum.mamas.domain.contract.exception.ContractAlreadyExistException;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.estate.EstateInfoDao;
import com.sondahum.mamas.domain.estate.exception.EstateAlreadyExistException;
import com.sondahum.mamas.domain.user.exception.UserAlreadyExistException;
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
        try {
            currentUser = userInfoDao.createUserInfo(userDto); // 이미 존재하면 있는유저 반환
        } catch (UserAlreadyExistException e) {
            currentUser = e.getUser();
            e.setMessage(userDto.getName() + "님의 정보가 이미 존재합니다.");
            throw e;
        }

        return currentUser;
    }

    // xhr로 수정분만 받아오니까
    // 방금 넣은 애만 return해주자
    public Estate addNewEstate(EstateDto.CreateReq estateDto) {
        Estate estate;
        try {
            estate = estateInfoDao.createEstateInfo(estateDto);
        } catch (EstateAlreadyExistException e) {
            estate = e.getEstate();
            e.setMessage("중복된 정보가 존재합니다. [ " +estate.getName()+" ]" );
            throw e;
        }

        return estate;
    }

    public Estate updateEstate(EstateDto.UpdateReq estateDto) {
        return estateInfoDao.updateEstateInfo(estateDto);
    }

    public List<Estate> deleteEstate(Long id) { // 삭제는 수행 후 리스트를 갱신해서 보여준다.
        estateInfoDao.deleteEstateInfo(id);
        return currentUser.getEstateList();
    }

    public Bid addNewBid(BidDto.CreateReq bidDto) {
        Bid bid;
        Estate estate;
        try{
            // 없던 땅이면 만든다. --> todo 소유주 정보를 입력해야한다.
            estate = estateInfoDao.createEstateInfo(
                    EstateDto.CreateReq.builder()
                    .name(bidDto.getEstateName())
                    .address(bidDto.getEstateAddress())
                    .build()
            );
        } catch (EstateAlreadyExistException ee) { // 이미 bidding한적이 있는 물건
            estate = ee.getEstate();
            // 있는 땅일 때는 주인도 누군지 알 수 있다.
            if (estate.getOwner().equals(currentUser) && bidDto.getAction().equals(Action.BUY)) {
                throw new InvalidActionException("자신의 땅은 살 수 없습니다.");
            }

            int amount=0, sell=0, buy=0;
            for (Bid bidHistory : currentUser.getBidList()) {
                if (bidHistory.getEstate().getId().equals(estate.getId())) {
                    amount++;
                    if (bidHistory.getAction().equals(Action.BUY))
                        buy++;
                    else sell++;
                }
            }
            ee.setMessage("총 "+amount+"건의 호가 기록이 있는 매물입니다.\n 매수 : "+buy+"매도 : "+sell);
        }

        try {
            bid = bidInfoDao.createBid(bidDto);
        } catch (BidAlreadyExistException be) { // 얘는 알려만 주는거다. 등록은 된다.
            bid = be.getBid();
            be.setMessage("중복된 정보가 존재합니다. [ "
                    + bid.getEstate()+", "+bid.getAction()+", "+bid.getPriceRange().toString()+" ]");
        }

        return bid;
    }

    public Bid updateBid(BidDto.UpdateReq bidDto) {
        return bidInfoDao.updateBidInfo(bidDto);
    }

    public List<Bid> deleteBid(Long id) {
        bidInfoDao.deleteBidInfo(id);
        return currentUser.getBidList();
    }

    public Contract addNewContract(ContractDto.CreateReq contractDto) {
        Contract contract;
        User seller;
        User buyer;
        Estate estate;

        try {
            seller = userInfoDao.createUserInfo(
                    UserDto.CreateReq.builder()
                            .name(contractDto.getSeller())
                            .build()
            );
        } catch (UserAlreadyExistException se) {
            seller = se.getUser();
        }

        try {
            buyer = userInfoDao.createUserInfo(
                    UserDto.CreateReq.builder()
                    .name(contractDto.getBuyer())
                    .build()
            );
        } catch (UserAlreadyExistException be) {
            buyer = be.getUser();
        }

        try {
            estate = estateInfoDao.createEstateInfo(
                    EstateDto.CreateReq.builder()
                    .name(contractDto.getEstate())
                    .build()
            );

            seller
        } catch (EstateAlreadyExistException ee) {
            estate = ee.getEstate();
        }



        try {
            contract = contractInfoDao.createContractInfo(contractDto);
        } catch (ContractAlreadyExistException e) {
            contract = e.getContract();
            e.setMessage("중복된 정보가 존재합니다. [" // todo 요건 좀 정리를 해야겠다. --> contract의 중복조건!!!
                    +contract.getEstate()+", "+"");
            throw e;
        }

        currentUser.getContractList().add(contract);
        return contract;
    }

    public Contract updateContract(ContractDto.UpdateReq contractDto) {
        return contractInfoDao.updateContractInfo(contractDto);
    }

    public List<Contract> deleteContract(Long id) {
        contractInfoDao.deleteContractInfo(id);
        return currentUser.getContractList();
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
