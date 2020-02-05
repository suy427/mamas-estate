package com.sondahum.mamas.service

import com.sondahum.mamas.elasticsearch.dto.ContractDto
import com.sondahum.mamas.elasticsearch.dto.SearchOption
import com.sondahum.mamas.elasticsearch.repository.ContractDao
import org.springframework.stereotype.Service

@Service
class ContractManageServiceImpl implements ContractManageService{

    private ContractDao contractDao

    ContractManageService(ContractDao contractDao) {
        this.contractDao = contractDao
    }

    @Override
    List<ContractDto> getContractInformation() {
        contractDao.retrieve()
    }

    @Override
    List<ContractDto> searchContractData(SearchOption searchOption) {
        contractDao.search(searchOption)
    }

    @Override
    List<ContractDto> updateContractData(List<ContractDto> contractList) {
        contractDao.update(contractList)
    }

    @Override
    List<ContractDto> deleteContractData(List<ContractDto> contractList) {
        contractDao.delete(contractList)
    }

    @Override
    void createContractData(List<ContractDto> contractList) {
        contractDao.save(contractList)
    }
}
