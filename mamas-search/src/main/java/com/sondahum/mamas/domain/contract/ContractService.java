package com.sondahum.mamas.domain.contract;

import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }


    public ContractDto.DetailResponse createContractInfo(ContractDto.CreateReq userDto) {
        return null;
    }

    public ContractDto.DetailResponse updateContractInfo(long id, ContractDto.UpdateReq dto) {
        return null;
    }

    public ContractDto.DetailResponse deleteContractInfo(Long id) {
        Optional<Contract> optional = contractRepository.findById(id);
        Contract contract = optional.orElseThrow(() -> new NoSuchEntityException(id)); // todo exception 정리

        contractRepository.deleteById(id);

        return new ContractDto.DetailResponse(contract);
    }

}
