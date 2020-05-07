package com.sondahum.mamas.domain.contract;


import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.estate.EstateInfoDao;
import com.sondahum.mamas.domain.estate.EstateRepository;
import com.sondahum.mamas.domain.estate.exception.EstateAlreadyExistException;
import com.sondahum.mamas.domain.estate.model.EstateStatus;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.domain.user.UserInfoDao;
import com.sondahum.mamas.domain.user.UserRepository;
import com.sondahum.mamas.domain.user.exception.UserAlreadyExistException;
import com.sondahum.mamas.dto.ContractDto;
import com.sondahum.mamas.dto.EstateDto;
import com.sondahum.mamas.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContractInfoService {

    private final UserInfoDao userInfoDao;
    private final EstateInfoDao estateInfoDao;
    private final ContractInfoDao contractInfoDao;

    public Contract createContractInfo(ContractDto.CreateReq contractDto) { // 맨 마지막
        Contract contract = contractInfoDao.createContractInfo(contractDto);

        // 없으면 만든다. --> specify에서 이미 만들었다.
        User seller = userInfoDao.findUserByName(contractDto.getSeller());
        User buyer = userInfoDao.findUserByName(contractDto.getBuyer());

        if (seller.getName().equals(buyer.getName())) {
            throw new RuntimeException(); // todo 스스로계약 안됨.
        }

        Estate estate = estateInfoDao.findEstateByName(contractDto.getEstate());

        if (!estate.getOwner().equals(seller)) {
            throw new RuntimeException(); // todo 잘못된 계약...
        }

        contract.setSeller(seller);
        contract.setBuyer(buyer);
        contract.setEstate(estate);

        seller.addContractHistory(contract);
        buyer.addContractHistory(contract);
        estate.addContractHistory(contract);

        return contract;
    }

    // seller, buyer 둘다쓰고, 중복체크는 클라이언트에서..
    // update할때도 이걸 돌리자 --> 업데이트는 이름만 --> 아니다 각각의 service에서 바꾸도록 하자.
    public User specifyUser(String name) {
        return userInfoDao.createUserInfo(UserDto.CreateReq.builder().name(name).build());
    }

    public Estate specifyEstate(String name) {
        return estateInfoDao.createEstateInfo(EstateDto.CreateReq.builder().name(name).build());
    }

    public Contract getContractById(long id) {
        return contractInfoDao.getContractById(id);
    }

    public Contract revertContract(long id) {
        Contract contractInfo = contractInfoDao.deleteContractInfo(id);

        contractInfo.getBuyer().getEstateList() // 산거 무효
                .removeIf(estate -> estate.equals(contractInfo.getEstate()));
        contractInfo.getSeller().addEstate(contractInfo.getEstate()); // 판거 무효
        contractInfo.getBuyer().getContractList() // 계약 무효
                .removeIf(contract -> contract.equals(contractInfo));
        contractInfo.getSeller().getContractList() // 계약 무효
                .removeIf(contract -> contract.equals(contractInfo));
        contractInfo.getEstate().getContractHistoryList() // 계약 무효
                .removeIf(contract -> contract.getId().equals(contractInfo.getId()));

        contractInfo.getEstate().setStatus(EstateStatus.ONSALE); // 땅 상태 판매중.

        return contractInfo;
    }

    // 계약 내용은 바뀔 수 있다 + estate의 명칭까지..! --> 계약 내용만 바뀌도록..!
    public Contract updateContractInfo(ContractDto.UpdateReq contractDto) {
        Contract contract = contractInfoDao.getContractById(contractDto.getId());
        contract.updateContractInfo(contractDto);

        return contract;
    }
}
