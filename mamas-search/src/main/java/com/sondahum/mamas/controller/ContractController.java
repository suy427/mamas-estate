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
@RequestMapping("/contract")
@Slf4j
@RequiredArgsConstructor
public class ContractController {

    private final ContractInfoService contractInfoService;
    private final ContractSearchService contractSearchService;
    private final UserInfoService userInfoService;
    private final EstateSearchService estateSearchService;
    private final UserSearchService userSearchService;
    private final EstateInfoService estateInfoService;

    @PostMapping
    public List<UserDto.SimpleForm> specifyUser(String query) {
        return userSearchService.specify(query).stream()
                .map(UserDto.SimpleForm::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public UserDto.SimpleForm setContractUser(UserDto.CreateReq userDto) {
        return new UserDto.SimpleForm(userInfoService.createUserInfo(userDto));
    }

    @PostMapping
    public List<EstateDto.SimpleForm> specifyEstate(String query) {
        return estateSearchService.specify(query).stream()
                .map(EstateDto.SimpleForm::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public EstateDto.SimpleForm setContractEstate(EstateDto.CreateReq estateDto) {
        return new EstateDto.SimpleForm(estateInfoService.createEstateInfo(estateDto));
    }

    @PostMapping
    public ContractDto.DetailForm createContract(@RequestBody @Valid ContractDto.CreateReq userDto) {
        return new ContractDto.DetailForm(contractInfoService.createContractInfo(userDto));
    }

    @GetMapping
    public Page<ContractDto.DetailForm> searchContracts(ContractDto.SearchReq query, PageRequest pageRequest) {
        return contractSearchService.search(query, pageRequest.of(query.getSortOrders())).map(ContractDto.DetailForm::new);
    }

    @PutMapping
    public ContractDto.DetailForm updateContractInfo(@RequestBody final ContractDto.UpdateReq dto) {
        return new ContractDto.DetailForm(contractInfoService.updateContractInfo(dto));
    }

    @GetMapping(value = "/{id}")
    public ContractDto.DetailForm getContractDetail(@PathVariable final long id) {
        return new ContractDto.DetailForm(contractInfoService.getContractById(id));
    }

    @DeleteMapping(value = "/{id}")
    public ContractDto.DetailForm revertContract(@PathVariable final long id) {
        return new ContractDto.DetailForm(contractInfoService.revertContract(id));
    }
}
