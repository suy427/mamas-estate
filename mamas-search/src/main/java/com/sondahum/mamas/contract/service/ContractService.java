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

    public ContractDto.Response createNewUserInfo(ContractDto.CreateReq userDto) {
        return null;
    }

    public ContractDto.Response updateUserInfo(long id, ContractDto.UpdateReq dto) {
        return null;
    }

    public ContractDto.Response getUserById(long id) {
        return null;
    }
}
