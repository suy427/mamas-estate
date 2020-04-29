package com.sondahum.mamas.domain.contract;

import com.sondahum.mamas.common.error.exception.EntityAlreadyExistException;
import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
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


    public Contract createContractInfo(ContractDto.CreateReq contractDto) {
        Optional<Contract> duplicatedContract =
                getDuplicatedContract(contractDto.getSeller(), contractDto.getBuyer(), contractDto.getEstate());

        if (duplicatedContract.isPresent())
            throw new EntityAlreadyExistException(contractDto.getEstate());

        return contractRepository.save(contractDto.toEntity());
    }

    public Contract getContractById(long id) {
        Optional<Contract> optionalContract = contractRepository.findById(id);
        optionalContract.orElseThrow(() -> new NoSuchEntityException(id));

        return optionalContract.get();
    }

    @Transactional(readOnly = true)
    public Optional<Contract> getDuplicatedContract(String seller, String buyer, String estate) {
        return contractRepository.findBySeller_NameAndBuyer_NameAndEstate_Name_AndActive(
                        seller, buyer, estate, true);
    }

    public Contract updateContractInfo(ContractDto.UpdateReq dto) {
        Optional<Contract> optionalContract = contractRepository.findById(dto.getId());
        Contract contract = optionalContract.orElseThrow(() -> new NoSuchEntityException(dto.getId()));

        contract.updateContractInfo(dto);

        return contract;
    }

    public Contract deleteContractInfo(Long id) {
        Optional<Contract> optionalContract = contractRepository.deleteByIdInQuery(id);

        return optionalContract.orElseThrow(() -> new NoSuchEntityException(id));
    }

}
