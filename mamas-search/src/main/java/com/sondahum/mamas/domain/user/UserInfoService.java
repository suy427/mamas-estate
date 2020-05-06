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
    public List<Estate> addNewEstate(long id, EstateDto.CreateReq estateDto) {
        User currentUser = userInfoDao.getUserById(id);
        Estate newEstate = estateInfoDao.createEstateInfo(estateDto);



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
        estateInfoDao.deleteEstateInfo(estateDto.getId());

        User currentUser = userInfoDao.findUserByName(estateDto.getName());
        currentUser.getEstateList().removeIf(estate -> estate.getId().equals(estateDto.getId()));

        return currentUser.getEstateList();
    }


    @Transactional(rollbackFor = Exception.class)
    public Bid addNewBid(EstateDto.CreateReq estateDto, BidDto.CreateReq bidDto) { // 땅부터 정하고 넘어가는걸로..!
        Estate estate = addNewEstate(estateDto);

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

    // bid가 갖고있는 정보 --> user, estate 하지만 user는 바뀔일이 없다. 함
    // 따라서 estate가 바뀐다면 해당 estate에서 지우고 바뀐 estate에 넣어주는게 필요
    @Transactional(rollbackFor = Exception.class)
    public Bid updateBid(BidDto.UpdateReq bidDto) {
        Bid target = currentUser.getBidList().stream()
                .filter(bid -> bid.getId().equals(bidDto.getId()))
                .findFirst().orElseThrow(() -> new NoSuchEntityException(bidDto.getId()));

        if (!target.getEstate().getName().equals(bidDto.getEstate())) { // estate를 바꾸는 경우
            target.getEstate().getBidList().removeIf(bid -> bid.equals(target));

            Estate newEstate = addNewEstate(EstateDto.CreateReq.builder().name(bidDto.getEstate()).build());
            target.setEstate(newEstate);
            newEstate.addBidHistory(target);
        }

        target.updateBidInfo(bidDto);
        return target;
    }

    public List<Bid> deleteBid(Long id) {
        bidInfoDao.deleteBidInfo(id);
        return currentUser.getBidList();
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ContractDto.DetailForm> addNewContract(long id, ContractDto.CreateReq contractDto) {
        User currentUser = userInfoDao.getUserById(id);

        currentUser.addContractHistory();

        return contract;
    }

    @Transactional(rollbackFor = Exception.class)
    public Contract updateContract(ContractDto.UpdateReq contractDto) { // todo 의존관계가 있는 정보는 따로 넣어주는게 낫지 않을까..?
        Contract target = currentUser.getContractList().stream()
                .filter(contract -> contract.getId().equals(contractDto.getId()))
                .findFirst().orElseThrow(() -> new NoSuchEntityException(contractDto.getId()));

        if (!target.getEstate().getName().equals(contractDto.getEstate())) { // estate가 바뀌는 경우
            target.getEstate().getContractHistoryList().removeIf(estate -> estate.equals(target));

            Estate newEstate = addNewEstate(EstateDto.CreateReq.builder().name(contractDto.getEstate()).build());
            newEstate.addContractHistory(target);
            target.setEstate(newEstate);
        }


        // 계약사항중 사람에 수정사항이 있을경우
        // 본인은 수정할 수 없고 상대방을 수정할 수 있다.
        if (!(target.getSeller().getName().equals(contractDto.getSeller())
                && target.getBuyer().getName().equals(contractDto.getBuyer()))) {
            User contractor;
            String newContractorName;

            if (contractDto.getBuyer().equals(currentUser.getName())) { // 내가 매수자면 상대방은 매도자
                contractor = target.getSeller();
                target.setSeller(contractor);
                newContractorName = contractDto.getSeller();
            } else {
                contractor = target.getBuyer();
                target.setBuyer(contractor);
                newContractorName = contractDto.getBuyer();
            }

            contractor.getContractList().removeIf(contract -> contract.equals(target));
            User newContractor = specifyUser(UserDto.CreateReq.builder().name(newContractorName).build());
            newContractor.addContractHistory(target);

            if (target.getSeller().equals(currentUser)) {
                target.setBuyer(newContractor);
            } else {
                target.setSeller(newContractor);
            }
        }

        target.updateContractInfo(contractDto);
        return target;
    }

    public List<Contract> deleteContract(Long id) {
        contractInfoDao.deleteContractInfo(id);
        return currentUser.getContractList();
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

    @Transactional(rollbackFor = Exception.class)
    public Estate addNewEstate(String userName, EstateDto.CreateReq estateDto) {
        User currentUser = userInfoDao.findUserByName(userName);
        Estate estate;

        try { // 새로 등록하는 매물
            estate = estateInfoDao.createEstateInfo(estateDto);
            estate.setOwner(currentUser);
        } catch (EstateAlreadyExistException e) { // 이미 있는데
            estate = e.getEstate();
            if (estate.getOwner() == null) { // 주인이 없으면 가능
                estate.setOwner(currentUser);
            } else {
                e.setMessage("이미 소유주가 있습니다. [ "+estate.getOwner().getName()+" ]");
                throw e;
            }
        }
        currentUser.addEstate(estate);

        return estate;
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
