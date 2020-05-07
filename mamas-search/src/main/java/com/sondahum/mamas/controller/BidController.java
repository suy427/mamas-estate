package com.sondahum.mamas.controller;

import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.bid.BidInfoService;
import com.sondahum.mamas.dto.BidDto;
import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.domain.bid.BidSearchService;
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


    @PostMapping
    public BidDto.DefailForm createBid(@RequestBody @Valid BidDto.CreateReq bidDto) {
        Bid created = bidInfoService.createBid(bidDto);
        return new BidDto.DefailForm(created);
    }

    @GetMapping
    public Page<BidDto.DefailForm> searchBids(@RequestParam(name = "query", required = false) final BidDto.SearchReq query, final PageRequest pageRequest) {
        return bidSearchService.search(query, pageRequest.of(query.getSortOrders())).map(DefailForm::new);
    }

    @GetMapping(value = "/{id}")
    public BidDto.DefailForm getBidDetail(@PathVariable final long id) {
        return new BidDto.DefailForm(bidInfoService.getBidById(id));
    }

    @PutMapping
    public BidDto.DefailForm updateBidInfo(@RequestBody final BidDto.UpdateReq dto) {
        return new BidDto.DefailForm(bidInfoService.updateBidInfo(dto));
    }

    @DeleteMapping(value = "/{id}")
    public BidDto.DefailForm deleteBid(@PathVariable final long id) {
        return new BidDto.DefailForm(bidInfoService.deleteBidInfo(id));
    }
}
