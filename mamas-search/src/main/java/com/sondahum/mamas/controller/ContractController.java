package com.sondahum.mamas.controller;

import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.domain.contract.ContractSearchService;
import com.sondahum.mamas.domain.contract.ContractInfoService;
import com.sondahum.mamas.dto.ContractDto;

import com.sondahum.mamas.dto.EstateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public ContractDto.DetailResponse createContract(@RequestBody @Valid ContractDto.CreateReq userDto) {
        return contractInfoService.createContractInfo(userDto);
    }

    @GetMapping
    public Page<ContractDto.DetailResponse> searchContracts( // 이걸로 검색과 전체 유저 불러오기 가능
                                                             @RequestParam(name = "value", required = false) final ContractDto.SearchReq query,
                                                             final PageRequest pageRequest
    ) {
        return contractSearchService.search(query, pageRequest.of(query.getSortOrders())).map(ContractDto.DetailResponse::new);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ContractDto.DetailResponse getEstateDetail(@PathVariable final long id) {
        return contractInfoService.getContractById(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ContractDto.DetailResponse updateContractInfo(@PathVariable final long id, @RequestBody final ContractDto.UpdateReq dto) {
        return contractInfoService.updateContractInfo(id, dto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ContractDto.DetailResponse deleteUser(@PathVariable final long id) {
        return contractInfoService.deleteContractInfo(id);
    }
}
