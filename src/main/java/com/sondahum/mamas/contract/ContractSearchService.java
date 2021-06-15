package com.sondahum.mamas.contract;


import com.sondahum.mamas.contract.adaptor.out.persistence.Contract;
import com.sondahum.mamas.contract.adaptor.out.persistence.ContractRepository;
import com.sondahum.mamas.dto.ContractDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class ContractSearchService {
    private final ContractRepository contractRepository;

    public ContractSearchService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Page<Contract> search(final ContractDto.SearchReq query) {
        return null;
    }
}
