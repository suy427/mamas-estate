package com.sondahum.mamas.domain.contract;

import com.sondahum.mamas.common.error.exception.EntityAlreadyExistException;
import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.dto.ContractDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractInfoService {

    private final ContractRepository contractRepository;


    public Contract createContractInfo(ContractDto.CreateReq contractDto) {
        if (!isSameContract(contractDto))
            throw new EntityAlreadyExistException(contractDto.getEstate());

        Contract contract = contractRepository.save(contractDto.toEntity());

        return contract;
    }

    public Contract getContractById(long id) {
        Optional<Contract> optionalContract = contractRepository.findById(id);
        optionalContract.orElseThrow(() -> new NoSuchEntityException(id));

        return optionalContract.get();
    }

    @Transactional(readOnly = true)
    boolean isSameContract(ContractDto.CreateReq contractDto) {
        Optional<Contract> optionalContract =
                contractRepository.findBySeller_NameAndBuyer_NameAndEstate_Name(
                        contractDto.getSeller(), contractDto.getBuyer(), contractDto.getEstate());

        return optionalContract.isPresent();
    }

    public Contract updateContractInfo(ContractDto.UpdateReq dto) {
        Optional<Contract> optionalContract = contractRepository.findById(dto.getId());
        Contract contract = optionalContract.orElseThrow(() -> new NoSuchEntityException(dto.getId()));

        contract.updateContractInfo(dto);

        return contract;
    }

    public Contract deleteContractInfo(Long id) {
        Optional<Contract> optional = contractRepository.findById(id);
        Contract contract = optional.orElseThrow(() -> new NoSuchEntityException(id)); // todo exception 정리

        contractRepository.deleteById(id);

        return contract;
    }

}
