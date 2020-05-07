package com.sondahum.mamas.domain.user;


import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.bid.BidInfoDao;
import com.sondahum.mamas.domain.bid.exception.BidAlreadyExistException;
import com.sondahum.mamas.domain.bid.exception.InvalidActionException;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.domain.contract.Contract;
import com.sondahum.mamas.domain.contract.ContractInfoDao;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.estate.EstateInfoDao;
import com.sondahum.mamas.domain.estate.exception.EstateAlreadyExistException;
import com.sondahum.mamas.domain.estate.model.EstateStatus;
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
public class UserInfoService {

    private final UserInfoDao userInfoDao;
    private final BidInfoDao bidInfoDao;
    private final EstateInfoDao estateInfoDao;
    private final ContractInfoDao contractInfoDao;

    public User createUserInfo(UserDto.CreateReq userDto) { // 기본정보 생성 --> 있으면 할필요 없다.
        User user;
        try {
            user = userInfoDao.createUserInfo(userDto);
        } catch (UserAlreadyExistException e) {
            user = e.getUser();
        }
        return user;
    }

    // xhr로 수정분만 받아오니까
    // 기본정보 외에 연관관계가 있는 정보들은 일단 유저 등록 후에 가능.
    @Transactional(rollbackFor = Exception.class)
    public Estate addNewEstate(EstateDto.CreateReq estateDto) {
        User currentUser = userInfoDao.findUserByName(estateDto.getOwnerName());
        Estate estate;

        try { // 새로 등록하는 매물
            estate = estateInfoDao.createEstateInfo(estateDto);
            estate.setOwner(currentUser);
        } catch (EstateAlreadyExistException e) { // 이미 있는데
            estate = e.getEstate();
            if (estate.getOwner() == null) { // 주인이 없으면 가능
                estate.setOwner(currentUser);
            } else {
                e.setMessage("이미 소유주가 있습니다. [ " + estate.getOwner().getName() + " ]");
                throw e;
            }
        }

        currentUser.addEstate(estate);
        return estate;
    }

    // estate가 갖고있을 수 있는 정보 --> owner, bid, contract
    // todo 그럼 얘도 다 가지고 있어야하나...? --> 복잡한 수정이 있어야할 경우엔, 아예 estate 관리로 넘어가자.
    // 그러니까 여기서는 기본정보 + owner정도까지만..!
    public Estate updateEstate(EstateDto.UpdateReq estateDto) {
        Estate originalEstate = estateInfoDao.getEstateById(estateDto.getId());
        return originalEstate.updateEstateInfo(estateDto);
    }

    // 여기서 땅을 지워버리게 되면, 이 땅의 bid, contract
    public List<Estate> deleteEstate(EstateDto.SimpleForm estateDto) { // 삭제는 수행 후 리스트를 갱신해서 보여준다.
        Estate target = estateInfoDao.deleteEstateInfo(estateDto.getId());

        User currentUser = target.getOwner();
        currentUser.getEstateList().removeIf(estate -> estate.getId().equals(estateDto.getId()));

        return currentUser.getEstateList();
    }


    @Transactional(rollbackFor = Exception.class)
    public Bid addNewBid(BidDto.CreateReq bidDto) { // 땅부터 정하고 넘어가는걸로..!
        Bid bid;
        User currentUser;
        Estate estate = estateInfoDao.findEstateByName(bidDto.getEstateName());

        if (estate.getOwner().getName().equals(bidDto.getUserName())) { // 내 땅.
            if (bidDto.getAction().equals(Action.BUY))
                throw new InvalidActionException("본인의 땅은 매수할 수 없습니다.");
        } else {
            if (bidDto.getAction().equals(Action.SELL))
                throw new InvalidActionException("타인 명의의 땅은 매도할 수 없습니다.");
        }

        if (estate.getStatus().equals(EstateStatus.SOLD)) {
            throw new InvalidActionException("이미 거래된 매물입니다.");
        }

        bid = bidInfoDao.createBid(bidDto);
        currentUser = userInfoDao.findUserByName(bidDto.getUserName());

        currentUser.addBidHistory(bid);
        estate.addBidHistory(bid);

        bid.setEstate(estate);
        bid.setUser(currentUser);

        return bid;
    }

    // bid가 갖고있는 정보 --> user, estate 하지만 user는 바뀔일이 없다.
    // --> 연관관계를 수정하는건 안되는걸로... 그냥 호가 내용만 수정 가능.
    @Transactional(rollbackFor = Exception.class)
    public Bid updateBid(BidDto.UpdateReq bidDto) {
        Bid originalBid = bidInfoDao.getBidById(bidDto.getId());
        return originalBid.updateBidInfo(bidDto);
    }

    public List<Bid> deleteBid(BidDto.DefailForm bidDto) {
        Bid target = bidInfoDao.deleteBidInfo(bidDto.getId());
        User currentUser = target.getUser();
        Estate currentEstate = target.getEstate();

        currentUser.getBidList().removeIf(bid -> bid.getId().equals(target.getId()));
        currentEstate.getBidList().removeIf(bid -> bid.getId().equals(target.getId()));

        return currentUser.getBidList();
    }

    // client에서 내가 매도/매수 고르게하고
    //
    @Transactional(rollbackFor = Exception.class)
    public Contract addNewContract(ContractDto.CreateReq contractDto) {
        User seller = userInfoDao.findUserByName(contractDto.getSeller());
        User buyer = userInfoDao.findUserByName(contractDto.getBuyer());;
        Estate estate = estateInfoDao.findEstateByName(contractDto.getEstate());

        if (seller.equals(buyer)) {
            throw new InvalidActionException("자가 계약은 불가능합니다.");
        }

        if (!estate.getOwner().equals(seller)) {
            throw new InvalidActionException("매도자 소유의 매물이 아닙니다. [ " +estate.getOwner().getName()+" ]");
        }

        Contract contract = contractInfoDao.createContractInfo(contractDto);
        contract.setSeller(seller);
        contract.setBuyer(buyer);
        contract.setEstate(estate);

        buyer.addContractHistory(contract);
        seller.addContractHistory(contract);

        seller.getEstateList().removeIf(property -> property.getId().equals(estate.getId()));
        buyer.addEstate(estate);

        return contract;
    }

    @Transactional(rollbackFor = Exception.class)
    public Contract updateContract(ContractDto.UpdateReq contractDto) {
        Contract originalContract = contractInfoDao.getContractById(contractDto.getId());

        return originalContract.updateContractInfo(contractDto);
    }

    public User getUserById(long id) {
        return userInfoDao.getUserById(id);
    }

    public User updateUserInfo(UserDto.UpdateReq dto) {
        return userInfoDao.updateUserInfo(dto);
    }

    public User deleteUserInfo(long id) {
        return userInfoDao.deleteUserInfo(id);
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

    private User specifyUser(UserDto.CreateReq userDto) {
        User user;

        try {
            user = userInfoDao.createUserInfo(userDto);
        } catch (UserAlreadyExistException e) {
            user = e.getUser();
            e.setMessage("중복된 정보가 존재합니다. [ " + user.getName() + ", " + user.getPhone() + " ]");
        }
        return user;
    }

    public Contract specifyContract(ContractDto.CreateReq contractDto) {
        return contractInfoDao.createContractInfo(contractDto);
    }
}
