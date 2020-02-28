package com.sondahum.mamas.controller;

import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.service.ContractService;
import com.sondahum.mamas.dto.ContractDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/contract")
public class ContractController {

    private static final Logger logger =  LoggerFactory.getLogger(ContractController.class);
    private final ContractService contractService;
//    private final ContractSearchService contractSearchService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
//        this.contractSearchService = contractSearchService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContractDto.DetailResponse createContract(@RequestBody @Valid ContractDto.CreateReq userDto) {
        return contractService.createContractInfo(userDto);
    }

    @GetMapping
    public Page<ContractDto.DetailResponse> searchContracts( // 이걸로 검색과 전체 유저 불러오기 가능
                                                             @RequestParam(name = "value", required = false) final ContractDto.SearchReq value,
                                                             final PageRequest pageRequest
    ) {
        return contractService.searchContracts(value, pageRequest.of()).map(ContractDto.DetailResponse::new);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ContractDto.DetailResponse updateContractInfo(@PathVariable final long id, @RequestBody final ContractDto.UpdateReq dto) {
        return contractService.updateContractInfo(id, dto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ContractDto.DetailResponse deleteUser(@PathVariable final long id) {
        return contractService.deleteContractInfo(id);
    }
}
