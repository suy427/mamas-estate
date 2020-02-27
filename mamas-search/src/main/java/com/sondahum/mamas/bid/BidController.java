package com.sondahum.mamas.bid;

import com.sondahum.mamas.bid.dto.BidDto;
import com.sondahum.mamas.common.model.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/bid")
public class BidController {

    private static final Logger logger =  LoggerFactory.getLogger(BidController.class);
    private final BidInfoService bidInfoService;
//    private final BidSearchService bidSearchService;

    public BidController(BidInfoService bidInfoService) {
        this.bidInfoService = bidInfoService;
//        this.bidSearchService = bidSearchService;
    }

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
        return bidInfoService.searchBid(query, pageRequest.of()).map(BidDto.DetailResponse::new);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public BidDto.DetailResponse updateBidInfo(@PathVariable final long id, @RequestBody final BidDto.UpdateReq dto) {
        return bidInfoService.updateUserInfo(id, dto);
    }
}
