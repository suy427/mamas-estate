package com.sondahum.mamas.controller;

import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.domain.bid.BidInfoService;
import com.sondahum.mamas.domain.contract.ContractInfoService;
import com.sondahum.mamas.domain.estate.EstateInfoService;
import com.sondahum.mamas.domain.user.UserInfoService;
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
    private final EstateInfoService estateInfoService;
    private final ContractInfoService contractInfoService;
    private final BidInfoService bidInfoService;


    @PostMapping
    public UserDto.DetailResponse createUser(@RequestBody @Valid UserDto.CreateReq userDto) {
        return new UserDto.DetailResponse(userInfoService.createUserInfo(userDto));
    }

    @PostMapping
    public EstateDto.SimpleForm addNewEstate(String userName, EstateDto.CreateReq estateDto) {
        return new EstateDto.SimpleForm(userInfoService.addNewEstate(userName, estateDto));
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
    public EstateDto.SimpleForm addNewContract(String userName, EstateDto.CreateReq estateDto) {
        return new EstateDto.SimpleForm(userInfoService.addNewEstate(userName, estateDto));
    }

    @PutMapping
    public EstateDto.SimpleForm updateContract(EstateDto.UpdateReq estateDto) {
        return new EstateDto.SimpleForm(userInfoService.updateEstate(estateDto));
    }

    @DeleteMapping
    public List<EstateDto.SimpleForm> deleteContract(EstateDto.SimpleForm estateDto) {
        return userInfoService.deleteEstate(estateDto).stream()
                .map(EstateDto.SimpleForm::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public EstateDto.SimpleForm addNewBid(String userName, EstateDto.CreateReq estateDto) {
        return new EstateDto.SimpleForm(userInfoService.addNewEstate(userName, estateDto));
    }

    @PutMapping
    public EstateDto.SimpleForm updateBid(EstateDto.UpdateReq estateDto) {
        return new EstateDto.SimpleForm(userInfoService.updateEstate(estateDto));
    }

    @DeleteMapping
    public List<EstateDto.SimpleForm> deleteBid(EstateDto.SimpleForm estateDto) {
        return userInfoService.deleteEstate(estateDto).stream()
                .map(EstateDto.SimpleForm::new)
                .collect(Collectors.toList());
    }

    @GetMapping
    public Page<UserDto.SimpleResponse> searchUsers(@RequestParam(name = "query", required = false) final UserDto.SearchReq query, final PageRequest pageRequest) {
        return userSearchService.search(query, pageRequest.of(query.getSortOrders())).map(UserDto.SimpleResponse::new);
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