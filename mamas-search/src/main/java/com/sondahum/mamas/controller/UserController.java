package com.sondahum.mamas.controller;

import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.domain.bid.BidInfoService;
import com.sondahum.mamas.domain.contract.ContractInfoService;
import com.sondahum.mamas.domain.estate.EstateInfoService;
import com.sondahum.mamas.domain.estate.EstateSearchService;
import com.sondahum.mamas.domain.user.UserInfoService;
import com.sondahum.mamas.dto.BidDto;
import com.sondahum.mamas.dto.ContractDto;
import com.sondahum.mamas.dto.EstateDto;
import com.sondahum.mamas.dto.UserDto;
import com.sondahum.mamas.domain.user.UserSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
class UserController {

    private final UserInfoService userInfoService;
    private final EstateInfoService estateInfoService;
    private final ContractInfoService contractInfoService;
    private final BidInfoService bidInfoService;

    private final UserSearchService userSearchService;
    private final EstateSearchService estateSearchService;


    @PostMapping(value = "/register")
    public UserDto.DetailForm createUser(@RequestBody @Valid UserDto.CreateReq userDto) {
        return new UserDto.DetailForm(userInfoService.createUserInfo(userDto));
    }

    // bidding할 때, bidding할 땅 찾을때.. --> contract할 때도 똑같이 쓴다.
    // 없으면 클라이언트에서 등록 폼 주고 addNewEstate를 한다. --> 없는건 못한다.
    // 결과로 나온 땅을 골라서 누르면 bid 입력폼으로 간다. --> contract입력폼도 가능. --> 이건 둘 다 클라에서..
    @GetMapping(value = "")
    public List<EstateDto.SimpleForm> specifyEstate(String query) {
        return estateSearchService.specify(query).stream()
                .map(EstateDto.SimpleForm::new)
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/estate")
    public EstateDto.SimpleForm addNewEstate(EstateDto.CreateReq estateDto) {
        return new EstateDto.SimpleForm(estateInfoService.createEstateInfo(estateDto));
    }

    @PutMapping
    public EstateDto.SimpleForm updateEstate(EstateDto.UpdateReq estateDto) {
        return new EstateDto.SimpleForm(estateInfoService.updateEstateInfo(estateDto));
    }

    @PutMapping
    public EstateDto.SimpleForm deleteEstate(EstateDto.SimpleForm estateDto) {
        return new EstateDto.SimpleForm(estateInfoService.deleteEstateInfo(estateDto.getId()));
    }

    @PostMapping
    public ContractDto.DetailForm addNewContract(ContractDto.CreateReq contractDto) {
        return new ContractDto.DetailForm(contractInfoService.createContractInfo(contractDto));
    }

    @PutMapping
    public ContractDto.DetailForm updateContract(ContractDto.UpdateReq contractDto) {
        return new ContractDto.DetailForm(contractInfoService.updateContractInfo(contractDto));
    }

    @PutMapping
    public ContractDto.DetailForm deleteContract(@PathVariable final long id) {
        return new ContractDto.DetailForm(contractInfoService.revertContract(id));
    }

    // specifyEstate에서 고른 땅이 박혀있는 채로 bid 입력 form 나옴
    //
    @PostMapping
    public BidDto.DetailForm addNewBid(BidDto.CreateReq bidDto) {
        return new BidDto.DetailForm(bidInfoService.addNewBid(bidDto));
    }

    @PutMapping
    public BidDto.DetailForm updateBid(BidDto.UpdateReq bidDto) {
        return new BidDto.DetailForm(bidInfoService.updateBid(bidDto));
    }

    @PutMapping
    public BidDto.DetailForm deleteBid(@PathVariable final long id) {
        return new BidDto.DetailForm(bidInfoService.deleteBidInfo(id));
    }

    @GetMapping
    public Page<UserDto.SimpleForm> searchUsers(UserDto.SearchReq query, PageRequest pageRequest) {
        return userSearchService.search(query, pageRequest.of(query.getSortOrders())).map(UserDto.SimpleForm::new);
    }

    @GetMapping(value = "/{id}")
    public UserDto.DetailForm getUserDetail(@PathVariable final long id) {
        return new UserDto.DetailForm(userInfoService.getUserById(id));
    }

    @PutMapping
    public UserDto.DetailForm updateUserInfo(@RequestBody final UserDto.UpdateReq dto) {
        return new UserDto.DetailForm(userInfoService.updateUserInfo(dto));
    }

    @DeleteMapping(value = "/{id}")
    public UserDto.DetailForm deleteUser(@PathVariable final long id) {
        return new UserDto.DetailForm(userInfoService.deleteUserInfo(id));
    }

}