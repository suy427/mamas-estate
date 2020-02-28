package com.sondahum.mamas.contract;

import com.sondahum.mamas.contract.dao.ContractRepository;
import com.sondahum.mamas.contract.domain.Contract;
import com.sondahum.mamas.contract.dto.ContractDto;
import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public ContractDto.DetailResponse deleteBidInfo(Long id) {
        Optional<Contract> optional = contractRepository.findById(id);
        Contract contract = optional.orElseThrow(() -> new NoSuchEntityException(id)); // todo exception 정리

        contractRepository.deleteById(id);

        return new ContractDto.DetailResponse(contract);
    }

    @Transactional(readOnly = true)
    public Page<Contract> searchContracts(final ContractDto.SearchReq query, final Pageable pageable) {
        Page<Contract> searchResult;

        if (query == null) {
            searchResult = contractRepository.findAll(pageable);
        } else {
            searchResult = null;
        }

        return searchResult;
    }
}
