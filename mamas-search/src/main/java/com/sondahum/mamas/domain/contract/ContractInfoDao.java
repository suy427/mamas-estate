package com.sondahum.mamas.domain.contract;

import com.sondahum.mamas.common.error.exception.EntityAlreadyExistException;
import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.domain.contract.exception.ContractAlreadyExistException;
import com.sondahum.mamas.dto.ContractDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractInfoDao {

    private final ContractRepository contractRepository;


    // contract의 duplicate는 expire만
    public Contract createContractInfo(ContractDto.CreateReq contractDto) {
        Optional<Contract> optionalContract = isDuplicated(contractDto);
        Contract contract;

        if (optionalContract.isPresent()) { // 있던거
            contract = optionalContract.get();
            if (contract.isValidAt(contractDto.getExpireDate())) { // 기한 내면 중복 불가
                throw new ContractAlreadyExistException(contract);
            } else {
                contract = contractRepository.save(contractDto.toEntity()); // 기한 지나면 중복 가능
            }
        } else {
            contract = contractRepository.save(contractDto.toEntity()); // 없던거면 저장
        }

        return contract;
    }

    public Contract getContractById(long id) {
        Optional<Contract> optionalContract = contractRepository.findById(id);
        optionalContract.orElseThrow(() -> new NoSuchEntityException(id));

        return optionalContract.get();
    }

    @Transactional(readOnly = true)
    public Optional<Contract> isDuplicated(ContractDto.CreateReq contractDto) {
        return contractRepository.findBySeller_NameAndBuyer_NameAndEstate_Name_AndActive(
                contractDto.getSeller()
                , contractDto.getBuyer()
                , contractDto.getEstate()
                , true);
    }

    public Contract updateContractInfo(ContractDto.UpdateReq dto) {
        Optional<Contract> optionalContract = contractRepository.findById(dto.getId());
        Contract contract = optionalContract.orElseThrow(() -> new NoSuchEntityException(dto.getId()));

        contract.updateContractInfo(dto);

        return contract;
    }

    public Contract softDeleteContract(Long id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(id));

        contract.setActive(false);
        return contract;
    }

    public void hardDeleteContract(long id) {
        contractRepository.deleteById(id);
    }

}
