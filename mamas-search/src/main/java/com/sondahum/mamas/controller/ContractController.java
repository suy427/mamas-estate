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
    public UserDto.SimpleResponse specifyUser(String name) {
        return new UserDto.SimpleResponse(contractInfoService.specifyUser(name));
    }

    @PostMapping
    public EstateDto.SimpleResponse specifyEstate(String name) {
        return new EstateDto.SimpleResponse(contractInfoService.specifyEstate(name));
    }

    @PostMapping
    public ContractDto.DetailResponse createContract(@RequestBody @Valid ContractDto.CreateReq userDto) {
        return new ContractDto.DetailResponse(contractInfoService.createContractInfo(userDto));
    }

    @GetMapping
    public Page<ContractDto.DetailResponse> searchContracts(@RequestParam(name = "query", required = false) final ContractDto.SearchReq query, final PageRequest pageRequest) {
        return contractSearchService.search(query, pageRequest.of(query.getSortOrders())).map(ContractDto.DetailResponse::new);
    }

    @PutMapping
    public ContractDto.DetailResponse updateContractInfo(@RequestBody final ContractDto.UpdateReq dto) {
        return new ContractDto.DetailResponse(contractInfoService.updateContractInfo(dto));
    }

    @GetMapping(value = "/{id}")
    public ContractDto.DetailResponse getEstateDetail(@PathVariable final long id) {
        return new ContractDto.DetailResponse(contractInfoService.getContractById(id));
    }

    @DeleteMapping(value = "/{id}")
    public ContractDto.DetailResponse revertContract(@PathVariable final long id) {
        return new ContractDto.DetailResponse(contractInfoService.revertContract(id));
    }
}
