package com.sondahum.mamas.controller;

import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.domain.contract.ContractInfoService;
import com.sondahum.mamas.domain.contract.ContractSearchService;
import com.sondahum.mamas.dto.ContractDto;

import com.sondahum.mamas.dto.EstateDto;
import com.sondahum.mamas.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/contract")
@Slf4j
@RequiredArgsConstructor
public class ContractController {

    private final ContractInfoService contractInfoService;
    private final ContractSearchService contractSearchService;

    @PostMapping
    public UserDto.SimpleForm specifyUser(String name) {
        return new UserDto.SimpleForm(contractInfoService.specifyUser(name));
    }

    @PostMapping
    public EstateDto.SimpleForm specifyEstate(String name) {
        return new EstateDto.SimpleForm(contractInfoService.specifyEstate(name));
    }

    @PostMapping
    public ContractDto.DetailForm createContract(@RequestBody @Valid ContractDto.CreateReq userDto) {
        return new ContractDto.DetailForm(contractInfoService.createContractInfo(userDto));
    }

    @GetMapping
    public Page<ContractDto.DetailForm> searchContracts(@RequestParam(name = "query", required = false) final ContractDto.SearchReq query, final PageRequest pageRequest) {
        return contractSearchService.search(query, pageRequest.of(query.getSortOrders())).map(ContractDto.DetailForm::new);
    }

    @PutMapping
    public ContractDto.DetailForm updateContractInfo(@RequestBody final ContractDto.UpdateReq dto) {
        return new ContractDto.DetailForm(contractInfoService.updateContractInfo(dto));
    }

    @GetMapping(value = "/{id}")
    public ContractDto.DetailForm getEstateDetail(@PathVariable final long id) {
        return new ContractDto.DetailForm(contractInfoService.getContractById(id));
    }

    @DeleteMapping(value = "/{id}")
    public ContractDto.DetailForm revertContract(@PathVariable final long id) {
        return new ContractDto.DetailForm(contractInfoService.revertContract(id));
    }
}
