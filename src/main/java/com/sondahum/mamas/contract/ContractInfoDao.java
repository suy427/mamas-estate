package com.sondahum.mamas.contract;

import com.sondahum.mamas.error.exception.NoSuchEntityException;
import com.sondahum.mamas.contract.adaptor.out.persistence.Contract;
import com.sondahum.mamas.contract.adaptor.out.persistence.ContractRepository;
import com.sondahum.mamas.contract.exception.ContractAlreadyExistException;
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
    public Contract createContractInfo(Contract contract) {
        Optional<Contract> optionalContract = isDuplicated(contract);
        Contract result;

        if (optionalContract.isPresent()) { // 있던거
            result = optionalContract.get();
            if (contract.isValidAt(contract.getExpireDate())) { // 기한 내면 중복 불가
                throw new ContractAlreadyExistException(contract);
            } else {
                result = contractRepository.save(contract); // 기한 지나면 중복 가능
            }
        } else {
            result = contractRepository.save(contract); // 없던거면 저장
        }

        return result;
    }

    public Contract getContractById(long id) {
        Optional<Contract> optionalContract = contractRepository.findById(id);
        optionalContract.orElseThrow(() -> new NoSuchEntityException(id));

        return optionalContract.get();
    }

    @Transactional(readOnly = true)
    public Optional<Contract> isDuplicated(Contract contract) {
        return contractRepository.findBySeller_NameAndBuyer_NameAndEstate_Name_AndActiveTrue(
                contract.getSeller().getName()
                , contract.getBuyer().getName()
                , contract.getEstate().getName());
    }

    public Contract updateContractInfo(long id, ContractDto.UpdateReq dto) {
        Optional<Contract> optionalContract = contractRepository.findById(id);
        Contract contract = optionalContract.orElseThrow(() -> new NoSuchEntityException(id));

        return contract.updateContractInfo(dto);
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
