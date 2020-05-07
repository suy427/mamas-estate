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
    private final UserSearchService userSearchService;
    private final EstateSearchService estateSearchService;
    private final EstateInfoService estateInfoService;
    private final ContractInfoService contractInfoService;
    private final BidInfoService bidInfoService;


    @PostMapping
    public UserDto.DetailResponse createUser(@RequestBody @Valid UserDto.CreateReq userDto) {
        return new UserDto.DetailResponse(userInfoService.createUserInfo(userDto));
    }

    @PostMapping
    public EstateDto.SimpleForm addNewEstate(EstateDto.CreateReq estateDto) {
        return new EstateDto.SimpleForm(userInfoService.addNewEstate(estateDto));
    }

    @PutMapping
    public EstateDto.SimpleForm updateEstate(EstateDto.UpdateReq estateDto) {
        return new EstateDto.SimpleForm(userInfoService.updateEstate(estateDto));
    }

    @DeleteMapping
    public List<EstateDto.SimpleForm> deleteEstate(EstateDto.SimpleForm estateDto) {
        return userInfoService.deleteEstate(estateDto).stream()
                .map(EstateDto.SimpleForm::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ContractDto.DetailForm addNewContract(ContractDto.CreateReq contractDto) {
        return new ContractDto.DetailForm(userInfoService.addNewContract(contractDto));
    }

    @PutMapping
    public ContractDto.DetailForm updateContract(ContractDto.UpdateReq contractDto) {
        return new ContractDto.DetailForm(userInfoService.updateContract(contractDto));
    }

    @DeleteMapping
    public ContractDto.DetailForm deleteContract(@PathVariable final long id) {
        return new ContractDto.DetailForm(contractInfoService.revertContract(id));
    }

    // bidding할 때, bidding할 땅 찾을때.. --> contract할 때도 똑같이 쓴다.
    // 없으면 클라이언트에서 등록 폼 주고 addNewEstate를 한다. --> 없는건 못한다.
    // 결과로 나온 땅을 골라서 누르면 bid 입력폼으로 간다. --> contract입력폼도 가능. --> 이건 둘 다 클라에서..
    @PostMapping
    public Page<EstateDto.SimpleForm> specifyEstate(
            @RequestParam(name = "query", required = false) final EstateDto.SearchReq query
            , final PageRequest pageRequest)
    {
                return estateSearchService.search(query, pageRequest.of(query.getSortOrders())).map(EstateDto.SimpleForm::new);
    }

    // specifyEstate에서 고른 땅이 박혀있는 채로 bid 입력 form 나옴
    //
    @PostMapping
    public BidDto.DefailForm addNewBid(BidDto.CreateReq bidDto) {
        return new BidDto.DefailForm(userInfoService.addNewBid(bidDto));
    }

    @PutMapping
    public BidDto.DefailForm updateBid(BidDto.UpdateReq bidDto) {
        return new BidDto.DefailForm(userInfoService.updateBid(bidDto));
    }

    @DeleteMapping
    public List<BidDto.DefailForm> deleteBid(BidDto.DefailForm bidDto) {
        return userInfoService.deleteBid(bidDto).stream()
                .map(BidDto.DefailForm::new)
                .collect(Collectors.toList());
    }

    @GetMapping
    public Page<UserDto.SimpleForm> searchUsers(@RequestParam(name = "query", required = false) final UserDto.SearchReq query, final PageRequest pageRequest) {
        return userSearchService.search(query, pageRequest.of(query.getSortOrders())).map(UserDto.SimpleForm::new);
    }

    @GetMapping(value = "/{id}")
    public UserDto.DetailResponse getUserDetail(@PathVariable final long id) {
        return new UserDto.DetailResponse(userInfoService.getUserById(id));
    }

    @PutMapping
    public UserDto.DetailResponse updateUserInfo(@RequestBody final UserDto.UpdateReq dto) {
        return new UserDto.DetailResponse(userInfoService.updateUserInfo(dto));
    }

    @DeleteMapping(value = "/{id}")
    public UserDto.DetailResponse deleteUser(@PathVariable final long id) {
        return new UserDto.DetailResponse(userInfoService.deleteUserInfo(id));
    }

}