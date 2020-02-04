package com.sondahum.mamas.elasticsearch.service

import com.sondahum.mamas.elasticsearch.dto.ContractDto
import com.sondahum.mamas.elasticsearch.dto.SearchOption

interface ContractManageService { // Contract 정보 CRUD, Aggregation 까지

    List<ContractDto> getContractInformation()
    List<ContractDto> searchContractData(SearchOption searchOption)
    List<ContractDto> updateContractData(List<ContractDto> contractList)
    List<ContractDto> deleteContractData(List<ContractDto> contractList)
    void createContractData(List<ContractDto> contractList)
}