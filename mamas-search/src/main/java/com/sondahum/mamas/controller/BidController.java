package com.sondahum.mamas.controller;

import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.bid.BidInfoService;
import com.sondahum.mamas.domain.estate.EstateSearchService;
import com.sondahum.mamas.domain.user.UserSearchService;
import com.sondahum.mamas.dto.BidDto;
import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.domain.bid.BidSearchService;
import com.sondahum.mamas.dto.ContractDto;
import com.sondahum.mamas.dto.EstateDto;
import com.sondahum.mamas.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/bid")
public class BidController {

    private final BidInfoService bidInfoService;
    private final BidSearchService bidSearchService;
    private final UserSearchService userSearchService;
    private final EstateSearchService estateSearchService;


    @PostMapping
    public Page<UserDto.SimpleForm> specifyUser(
            @RequestParam(name = "query", required = false) final UserDto.SearchReq query
            , final PageRequest pageRequest)
    {
        return userSearchService.search(query, pageRequest.of(query.getSortOrders())).map(UserDto.SimpleForm::new);
    }

    @PostMapping
    public Page<EstateDto.SimpleForm> specifyEstate(
            @RequestParam(name = "query", required = false) final EstateDto.SearchReq query
            , final PageRequest pageRequest)
    {
        return estateSearchService.search(query, pageRequest.of(query.getSortOrders())).map(EstateDto.SimpleForm::new);
    }

    @PostMapping
    public BidDto.DetailForm createBid(@RequestBody @Valid BidDto.CreateReq bidDto) {
        Bid created = bidInfoService.createBid(bidDto);
        return new BidDto.DetailForm(created);
    }

    @GetMapping
    public Page<BidDto.DetailForm> searchBids(@RequestParam(name = "query", required = false) final BidDto.SearchReq query, final PageRequest pageRequest) {
        return bidSearchService.search(query, pageRequest.of(query.getSortOrders())).map(BidDto.DetailForm::new);
    }

    @GetMapping(value = "/{id}")
    public BidDto.DetailForm getBidDetail(@PathVariable final long id) {
        return new BidDto.DetailForm(bidInfoService.getBidById(id));
    }

    @PutMapping
    public BidDto.DetailForm updateBidInfo(@RequestBody final BidDto.UpdateReq dto) {
        return new BidDto.DetailForm(bidInfoService.updateBidInfo(dto));
    }

    @DeleteMapping(value = "/{id}")
    public BidDto.DetailForm revertContract(@PathVariable final long id) {
        return new BidDto.DetailForm(bidInfoService.revertBid(id));
    }

    @DeleteMapping(value = "/{id}")
    public BidDto.DetailForm deleteBid(@PathVariable final long id) {
        return new BidDto.DetailForm(bidInfoService.deleteBidInfo(id));
    }
}
