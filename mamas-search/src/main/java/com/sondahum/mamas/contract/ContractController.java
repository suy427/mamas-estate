package com.sondahum.mamas.contract;

import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.contract.dto.ContractDto;
import com.sondahum.mamas.contract.service.ContractSearchService;
import com.sondahum.mamas.contract.service.ContractService;

import com.sondahum.mamas.user.domain.UserSearchFilter;
import com.sondahum.mamas.user.dto.UserDto;
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
    private final ContractSearchService contractSearchService;

    public ContractController(ContractService contractService, ContractSearchService contractSearchService) {
        this.contractService = contractService;
        this.contractSearchService = contractSearchService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContractDto.Response createNewEstate(@RequestBody @Valid ContractDto.CreateReq userDto) {
        return contractService.createNewUserInfo(userDto);
    }

    @GetMapping
    public Page<ContractDto.Response> getEstates( // 이걸로 검색과 전체 유저 불러오기 가능
                                            @RequestParam(name = "type") final UserSearchFilter filter,
                                            @RequestParam(name = "value", required = false) final String value,
                                            final PageRequest pageRequest
    ) {
        return contractSearchService.search(filter, value, pageRequest.of()).map(ContractDto.Response::new);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ContractDto.Response getEstate(@PathVariable final long id) {
        return contractService.getUserById(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ContractDto.Response updateEstateInfo(@PathVariable final long id, @RequestBody final ContractDto.UpdateReq dto) {
        return contractService.updateUserInfo(id, dto);
    }
}