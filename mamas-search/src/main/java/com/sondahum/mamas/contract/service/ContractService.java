package com.sondahum.mamas.contract.service;

import com.sondahum.mamas.contract.dao.ContractRepository;
import com.sondahum.mamas.contract.dto.ContractDto;
import org.springframework.stereotype.Service;

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
}
