package com.sondahum.mamas.controller;

import com.sondahum.mamas.domain.bid.BidInfoService;
import com.sondahum.mamas.dto.BidDto;
import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.domain.bid.BidSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/bid")
@Slf4j
@RequiredArgsConstructor
public class BidController {

    private final BidInfoService bidInfoService;
    private final BidSearchService bidSearchService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BidDto.DetailResponse createBid(@RequestBody @Valid BidDto.CreateReq bidDto) {
        return bidInfoService.createBid(bidDto);
    }

    @GetMapping
    public Page<BidDto.DetailResponse> searchBids( // 이걸로 검색과 전체 유저 불러오기 가능
                                                   @RequestParam(name = "value", required = false) final BidDto.SearchReq query,
                                                   final PageRequest pageRequest
    ) {
        return bidSearchService.search(query, pageRequest.of(query.getSortOrders())).map(BidDto.DetailResponse::new);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public BidDto.DetailResponse getEstateDetail(@PathVariable final long id) {
        return bidInfoService.getBidById(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public BidDto.DetailResponse updateBidInfo(@PathVariable final long id, @RequestBody final BidDto.UpdateReq dto) {
        return bidInfoService.updateBidInfo(id, dto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public BidDto.DetailResponse deleteBid(@PathVariable final long id) {
        return bidInfoService.deleteBidInfo(id);
    }
}
