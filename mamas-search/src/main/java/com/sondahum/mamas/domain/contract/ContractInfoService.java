package com.sondahum.mamas.domain.contract;

import com.sondahum.mamas.common.error.exception.EntityAlreadyExistException;
import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.dto.ContractDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ContractInfoService {

    private final ContractRepository contractRepository;

    public ContractInfoService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }


    public ContractDto.DetailResponse createContractInfo(ContractDto.CreateReq contractDto) { //
        if (isSameContract(contractDto))
            throw new EntityAlreadyExistException(contractDto.getEstate());

        Contract contract = contractRepository.save(contractDto.toEntity());

        return new ContractDto.DetailResponse(contract);
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
