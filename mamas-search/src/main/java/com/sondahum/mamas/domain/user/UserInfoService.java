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
import com.sondahum.mamas.domain.estate.model.ContractType;
import com.sondahum.mamas.domain.user.exception.UserAlreadyExistException;
import com.sondahum.mamas.dto.BidDto;
import com.sondahum.mamas.dto.ContractDto;
import com.sondahum.mamas.dto.EstateDto;
import com.sondahum.mamas.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.tree.ExpandVetoException;
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

    public User createUserInfo(UserDto.CreateReq userDto) { // 기본정보 생성
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
    @Transactional(rollbackFor = Exception.class)
    public Estate addNewEstate(EstateDto.CreateReq estateDto) {
        Estate estate = specifyEstate(estateDto); // 일단 영속화부터 시키고보니까, 이게 실패하면 다시 지워줘야한다 (not soft delete)

        if (!estate.getOwner().equals(currentUser)) {
            if (estate.getOwner() == null)
                estate.setOwner(currentUser);
            else
                throw new UserAlreadyExistException(estate.getOwner()); // 다른 사람의 땅을 등록하려고 했을 때.
        } else {
            throw new EstateAlreadyExistException(estate); // 이미 있는 땅을 등록하려고 했을 때.
        }

        currentUser.addEstate(estate);
        return estate;
    }

    public Estate updateEstate(EstateDto.UpdateReq estateDto) {
        Estate target = currentUser.getEstateList().stream()
                .filter(estate -> estate.getId().equals(estateDto.getId()))
                .findFirst().orElseThrow(() -> new NoSuchEntityException(estateDto.getId()));

        target.updateEstateInfo(estateDto); // 예상) 영속화된 객체는 업데이트 됐을 때, 다른 객체와의 연관관계에서도 변경사항이 반영되있을 것이다.
        return target;
    }

    // 여기서 땅을 지워버리게 되면, 이 땅의 bid, contract
    public List<Estate> deleteEstate(Long id) { // 삭제는 수행 후 리스트를 갱신해서 보여준다.
        estateInfoDao.deleteEstateInfo(id);
        return currentUser.getEstateList();
    }


    @Transactional(rollbackFor = Exception.class)
    public Bid addNewBid(EstateDto.CreateReq estateDto, BidDto.CreateReq bidDto) { // 땅부터 정하고 넘어가는걸로..!
        Estate estate = specifyEstate(estateDto);

        if (estate.getOwner().equals(currentUser)) {
            if (bidDto.getAction().equals(Action.BUY) || bidDto.getAction().equals(Action.LEASE))
                throw new InvalidActionException("자신의 땅은 사거나 임대할  없습니다.");
        }

        Bid bid = specifyBid(bidDto);

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

        target.updateBidInfo(bidDto);
        return target;
    }

    public List<Bid> deleteBid(Long id) {
        bidInfoDao.deleteBidInfo(id);
        return currentUser.getBidList();
    }

    @Transactional(rollbackFor = Exception.class)
    public Contract addNewContract(EstateDto.CreateReq estateDto, ContractDto.CreateReq contractDto) {
        Estate estate = specifyEstate(estateDto);

        if (estate.getOwner().equals(currentUser) && contractDto.getBuyer().equals(currentUser.getName())) {
            throw new InvalidActionException("자신의 땅은 사거나 임대할 수 없습니다.");
        }

        User seller = userInfoDao.createUserInfo(UserDto.CreateReq.builder().name(contractDto.getSeller()).build());
        User buyer = userInfoDao.createUserInfo(UserDto.CreateReq.builder().name(contractDto.getBuyer()).build());
        Contract contract = contractInfoDao.createContractInfo(contractDto);

        if (contractDto.getContractType().equals(ContractType.BARGAIN)) {// 소유관계가 바뀌어버림
            seller.getEstateList().removeIf(e -> e.equals(estate));
            buyer.addEstate(estate);
            estate.setOwner(buyer);
        } else { //todo 임대 계약에 대해서 좀 더 생각해 볼것...!
            seller.addEstate(estate);
            estate.setOwner(seller);
        }
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

    private Estate specifyEstate(EstateDto.CreateReq estateDto) {
        Estate estate;

        try {
            estate = estateInfoDao.createEstateInfo(estateDto);
        } catch (EstateAlreadyExistException e) {
            estate = e.getEstate();
            e.setMessage("중복된 정보가 존재합니다. [ " + estate.getName() + " ]");
        }
        return estate;
    }

    private Bid specifyBid(BidDto.CreateReq bidDto) {
        Bid bid;

        try {
            bid = bidInfoDao.createBid(bidDto);
        } catch (BidAlreadyExistException e) {
            bid = e.getBid();
            e.setMessage("중복된 정보가 존재합니다. [ "
                    + bid.getEstate()+", "+bid.getAction()+", "+bid.getPriceRange().toString()+" ]");
        }
        return bid;
    }
}
