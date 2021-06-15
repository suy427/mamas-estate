package com.sondahum.mamas.contract.adaptor.in;

import com.sondahum.mamas.contract.ContractInfoService;
import com.sondahum.mamas.contract.ContractSearchService;
import com.sondahum.mamas.dto.ContractDto;

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
    public Page<ContractDto.DetailForm> searchContracts(ContractDto.SearchReq query) {
        return contractSearchService.search(query).map(ContractDto.DetailForm::new);
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
