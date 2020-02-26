package com.sondahum.mamas.bid;

import com.sondahum.mamas.bid.dto.BidDto;
import com.sondahum.mamas.bid.service.BidInfoService;
import com.sondahum.mamas.bid.service.BidSearchService;
import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.contract.dto.ContractDto;
import com.sondahum.mamas.user.domain.UserSearchFilter;
import com.sondahum.mamas.user.dto.UserDto;
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
    private final BidSearchService bidSearchService;

    public BidController(BidInfoService bidInfoService, BidSearchService bidSearchService) {
        this.bidInfoService = bidInfoService;
        this.bidSearchService = bidSearchService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BidDto.Response createNewBid(@RequestBody @Valid BidDto.CreateReq bidDto) {
        return bidInfoService.createNewUserInfo(bidDto);
    }

    @GetMapping
    public Page<BidDto.Response> getBids( // 이걸로 검색과 전체 유저 불러오기 가능
                                                @RequestParam(name = "type") final UserSearchFilter filter,
                                                @RequestParam(name = "value", required = false) final String value,
                                                final PageRequest pageRequest
    ) {
        return bidSearchService.search(filter, value, pageRequest.of()).map(BidDto.Response::new);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public BidDto.Response getBid(@PathVariable final long id) {
        return bidInfoService.getUserById(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public BidDto.Response updateBidInfo(@PathVariable final long id, @RequestBody final BidDto.UpdateReq dto) {
        return bidInfoService.updateUserInfo(id, dto);
    }
}
