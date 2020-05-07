package com.sondahum.mamas.controller;

import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.domain.bid.BidInfoService;
import com.sondahum.mamas.domain.contract.ContractInfoService;
import com.sondahum.mamas.domain.estate.EstateInfoService;
import com.sondahum.mamas.domain.user.UserInfoService;
import com.sondahum.mamas.domain.user.UserSearchService;
import com.sondahum.mamas.dto.BidDto;
import com.sondahum.mamas.dto.ContractDto;
import com.sondahum.mamas.dto.EstateDto;
import com.sondahum.mamas.domain.estate.EstateSearchService;
import com.sondahum.mamas.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/estate")
@RequiredArgsConstructor
@Slf4j
public class EstateController {

    private final EstateInfoService estateInfoService;
    private final EstateSearchService estateSearchService;
    private final UserSearchService userSearchService;
    private final UserInfoService userInfoService;
    private final BidInfoService bidInfoService;
    private final ContractInfoService contractInfoService;


    // 주인 검색 해서 고름. 없으면 setOwner로
    // 얘는 bid 등록할 때도 쓴다. --> null일 경우 또 setOwner로 감.
    @PostMapping
    public List<UserDto.SimpleForm> specifyOwner(String query) {
        return userSearchService.specify(query).stream()
                .map(UserDto.SimpleForm::new)
                .collect(Collectors.toList());
    }

    @PostMapping // 정보가 없는 새로운 유저일 경우 입력 form채워서 만듦.
    public UserDto.SimpleForm setOwner(UserDto.CreateReq userDto) {
        return new UserDto.SimpleForm(userInfoService.createUserInfo(userDto));
    }

    @PostMapping
    public EstateDto.DetailForm createEstate(@RequestBody @Valid EstateDto.CreateReq estateDto) {
        return new EstateDto.DetailForm(estateInfoService.createEstateInfo(estateDto));
    }

    @GetMapping
    public Page<EstateDto.SimpleForm> searchEstates(@RequestParam(name = "query", required = false) final EstateDto.SearchReq query, final PageRequest pageRequest) {
        return estateSearchService.search(query, pageRequest.of(query.getSortOrders())).map(EstateDto.SimpleForm::new);
    }

    @GetMapping(value = "/{id}")
    public EstateDto.DetailForm getEstateDetail(@PathVariable final long id) {
        return new EstateDto.DetailForm(estateInfoService.getEstateById(id));
    }

    @PostMapping
    public BidDto.DetailForm addNewBid(BidDto.CreateReq bidDto) {
        return new BidDto.DetailForm(bidInfoService.addNewBid(bidDto));
    }

    @PutMapping
    public BidDto.DetailForm updateBid(BidDto.UpdateReq bidDto) {
        return new BidDto.DetailForm(bidInfoService.updateBid(bidDto));
    }

    @DeleteMapping(value = "/{id}")
    public BidDto.DetailForm revertBid(@PathVariable final long id) {
        return new BidDto.DetailForm(bidInfoService.revertBid(id));
    }

    // 얘도 specifyUser 거져서 와야한다.
    @PostMapping
    public ContractDto.DetailForm addNewContract(ContractDto.CreateReq contractDto) {
        return new ContractDto.DetailForm(contractInfoService.createContractInfo(contractDto));
    }

    @PostMapping
    public ContractDto.DetailForm updateContract(ContractDto.UpdateReq contractDto) {
        return new ContractDto.DetailForm(contractInfoService.updateContractInfo(contractDto));
    }

    @DeleteMapping(value = "/{id}")
    public ContractDto.DetailForm revertContract(@PathVariable final long id) {
        return new ContractDto.DetailForm(contractInfoService.revertContract(id));
    }

    @PutMapping
    public EstateDto.DetailForm updateEstateInfo(@RequestBody final EstateDto.UpdateReq dto) {
        return new EstateDto.DetailForm(estateInfoService.updateEstateInfo(dto));
    }

    @DeleteMapping(value = "/{id}")
    public EstateDto.SimpleForm deleteEstate(@PathVariable final long id) {
        return new EstateDto.SimpleForm(estateInfoService.deleteEstateInfo(id));
    }
}
