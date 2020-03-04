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

    @PersistenceContext
    private final EntityManager em;


    public ContractDto.DetailResponse createContractInfo(ContractDto.CreateReq contractDto) { //
        if (isSameContract(contractDto))
            throw new EntityAlreadyExistException(contractDto.getEstate());

        Contract contract = contractRepository.save(contractDto.toEntity());

        return new ContractDto.DetailResponse(contract);
    }

    public ContractDto.DetailResponse getContractById(long id) {
        Optional<Contract> optionalContract = contractRepository.findById(id);
        optionalContract.orElseThrow(() -> new NoSuchEntityException(id));

        return new ContractDto.DetailResponse(optionalContract.get());
    }

    @Transactional(readOnly = true)
    boolean isSameContract(ContractDto.CreateReq contractDto) {
        Optional<Contract> optionalContract =
                contractRepository.findBySeller_NameAndBuyer_NameAndEstate_Name(
                        contractDto.getSeller(), contractDto.getBuyer(), contractDto.getEstate());

        return optionalContract.isPresent();
    }

    public ContractDto.DetailResponse updateContractInfo(long id, ContractDto.UpdateReq dto) {
        Optional<Contract> optionalContract = contractRepository.findById(id);
        Contract contract = optionalContract.orElseThrow(() -> new NoSuchEntityException(id));

        em.flush();
        contract.updateContractInfo(dto);

        return new ContractDto.DetailResponse(contract);
    }

    public ContractDto.DetailResponse deleteContractInfo(Long id) {
        Optional<Contract> optional = contractRepository.findById(id);
        Contract contract = optional.orElseThrow(() -> new NoSuchEntityException(id)); // todo exception 정리

        contractRepository.deleteById(id);

        return new ContractDto.DetailResponse(contract);
    }

}
