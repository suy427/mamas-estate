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

        Estate estate = specifyEstate(estateDto);

        estate.setOwner(currentUser);
        currentUser.addEstate(estate);

        return estate;
    }

    public Estate updateEstate(EstateDto.UpdateReq estateDto) {
        Estate target = currentUser.getEstateList().stream()
                .filter(estate -> estate.getId().equals(estateDto.getId()))
                .findFirst().orElseThrow(() -> new NoSuchEntityException(estateDto.getId()));

        target.updateEstateInfo(estateDto);
        return target;
    }

    public List<Estate> deleteEstate(Long id) { // 삭제는 수행 후 리스트를 갱신해서 보여준다.
        estateInfoDao.deleteEstateInfo(id);
        return currentUser.getEstateList();
    }

    private Estate specifyEstate(EstateDto.CreateReq estateDto) {
        Estate estate;

        try {
            estate = estateInfoDao.getDuplicatedEstate(estateDto.getName(), estateDto.getAddress())
                    .orElse(estateDto.toEntity());
        } catch (EstateAlreadyExistException e) {
            estate = e.getEstate();
            e.setMessage("중복된 정보가 존재합니다. [ " +estate.getName()+" ]" );
        }
        return estate;
    }

    public Bid addNewBid(EstateDto.CreateReq estateDto, BidDto.CreateReq bidDto) { // 땅부터 정하고 넘어가는걸로..!
        Bid bid;
        Estate estate;

        estate = specifyEstate(estateDto);

        // 이미 bidding한적이 있는 물건.
        // 있는 땅일 때는 주인도 누군지 알 수 있다.
        if (estate.getId() != 0) { // toEntity로 만든 entity는 id값이 아직 없다.
            if (estate.getOwner().equals(currentUser) && bidDto.getAction().equals(Action.BUY)) {
                throw new InvalidActionException("자신의 땅은 살 수 없습니다.");
            }
        }

        try {
            bid = bidInfoDao.getDuplicatedBid(bidDto.getUserName(), bidDto.getEstateName(), bidDto.getAction())
            .orElse(bidDto.toEntity());
        } catch (BidAlreadyExistException be) { // 얘는 알려만 주는거다. 등록은 된다.
            bid = be.getBid();
            be.setMessage("중복된 정보가 존재합니다. [ "
                    + bid.getEstate()+", "+bid.getAction()+", "+bid.getPriceRange().toString()+" ]");
        }

        bid.setUser(currentUser);
        bid.setEstate(estate);

        estate.addBidHistory(bid);
        currentUser.addBidHistory(bid);

        return bid;
    }

    public Bid updateBid(BidDto.UpdateReq bidDto) {
        Bid target = currentUser.getBidList().stream()
                .filter(bid -> bid.getId().equals(bidDto.getId()))
                .findFirst().orElseThrow(() -> new NoSuchEntityException(bidDto.getId()));

        //이 target객체는 영속성 객체니까 estate의 bidlist에도 변경이 반영이 되겠지...?
        //todo 확인 해봅시다.

        target.updateBidInfo(bidDto);
        return target;
    }

    public List<Bid> deleteBid(Long id) {
        bidInfoDao.deleteBidInfo(id);
        return currentUser.getBidList();
    }

    public Contract addNewContract(EstateDto.CreateReq estateDto, ContractDto.CreateReq contractDto) {
        Contract contract;
        User seller;
        User buyer;
        Estate estate;

        estate = specifyEstate(estateDto);

        if (estate.getId() != 0) {
            if(estate.getOwner().equals(currentUser) && contractDto.getBuyer().equals(currentUser.getName())) {
                throw new InvalidActionException("자신의 땅은 살 수 없습니다.");
            }
        }

        try {
            seller = userInfoDao.getDuplicatedUser(contractDto.getSeller())
            .orElse(UserDto.CreateReq.builder().name(contractDto.getSeller()).build().toEntity());
        } catch (UserAlreadyExistException se) {
            seller = se.getUser();
        }

        try {
            buyer = userInfoDao.getDuplicatedUser(contractDto.getBuyer())
                    .orElse(UserDto.CreateReq.builder().name(contractDto.getBuyer()).build().toEntity());
        } catch (UserAlreadyExistException be) {
            buyer = be.getUser();
        }

        try {
            contract = contractInfoDao.getDuplicatedContract(seller.getName(), buyer.getName(), estate.getName())
                    .orElse(ContractDto.CreateReq.builder()
                            .seller(seller.getName())
                            .buyer(buyer.getName())
                            .estate(estate.getName())
                            .build()
                            .toEntity()
                    );
        } catch (ContractAlreadyExistException ce) {
            contract = ce.getContract();
        }

        seller.addEstate(estate);
        estate.setOwner(seller);

        contract.setSeller(seller);
        contract.setBuyer(buyer);
        contract.setEstate(estate);

        buyer.addContractHistory(contract);
        seller.addContractHistory(contract);

        return contract;
    }

    public Contract updateContract(ContractDto.UpdateReq contractDto) {
        Contract target = currentUser.getContractList().stream()
                .filter(contract -> contract.getId().equals(contractDto.getId()))
                .findFirst().orElseThrow(() -> new NoSuchEntityException(contractDto.getId()));

        target.updateContractInfo(contractDto);
        return target;
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
