package com.sondahum.mamas.controller;

import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.domain.contract.ContractInfoService;
import com.sondahum.mamas.domain.contract.ContractSearchService;
import com.sondahum.mamas.domain.estate.EstateInfoService;
import com.sondahum.mamas.domain.estate.EstateSearchService;
import com.sondahum.mamas.domain.user.UserInfoService;
import com.sondahum.mamas.domain.user.UserSearchService;
import com.sondahum.mamas.dto.ContractDto;

import com.sondahum.mamas.dto.EstateDto;
import com.sondahum.mamas.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contracts")
@Slf4j
@RequiredArgsConstructor
public class ContractController {

    private final ContractInfoService contractInfoService;
    private final ContractSearchService contractSearchService;


    @PostMapping(value = "/contract")
    public ContractDto.DetailForm createContract(@RequestBody @Valid ContractDto.CreateReq userDto) {
        return new ContractDto.DetailForm(contractInfoService.createContractInfo(userDto));
    }

    @GetMapping
    public Page<ContractDto.DetailForm> searchContracts(ContractDto.SearchReq query, PageRequest pageRequest) {
        return contractSearchService.search(query, pageRequest.of()).map(ContractDto.DetailForm::new);
    }

    @GetMapping(value = "/user/{userId}")
    public List<ContractDto.DetailForm> getUserContractList(@PathVariable long userId) {
        return contractInfoService.getUserContractList(userId).stream()
                .map(ContractDto.DetailForm::new)
                .collect(Collectors.toList());
    }

    @PutMapping(value = "/{id}")
    public ContractDto.DetailForm updateContractInfo(@PathVariable final long id, @RequestBody final ContractDto.UpdateReq dto) {
        return new ContractDto.DetailForm(contractInfoService.updateContractInfo(id, dto));
    }

    @GetMapping(value = "/{id}")
    public ContractDto.DetailForm getContractDetail(@PathVariable final long id) {
        return new ContractDto.DetailForm(contractInfoService.getContractById(id));
    }

    @PutMapping(value = "/revert/{id}")
    public ContractDto.DetailForm revertContract(@PathVariable final long id) {
        return new ContractDto.DetailForm(contractInfoService.revertContract(id));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteContract(@PathVariable final long id) {
        contractInfoService.deleteContract(id);
    }
}
