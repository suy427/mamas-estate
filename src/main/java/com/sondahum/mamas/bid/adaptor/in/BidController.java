package com.sondahum.mamas.bid.adaptor.in;

import com.sondahum.mamas.bid.BidDto;
import com.sondahum.mamas.bid.BidInfoService;
import com.sondahum.mamas.bid.BidSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/bids")
public class BidController {
    private final BidInfoService bidInfoService;
    private final BidSearchService bidSearchService;

    @PostMapping(value = "/bid")
    public BidDto.DetailForm createBid(@RequestBody @Valid BidDto.CreateReq bidDto) {
        return new BidDto.DetailForm(bidInfoService.createBid(bidDto));
    }

    @GetMapping
    public Page<BidDto.DetailForm> searchBids(BidDto.SearchReq query) {
        return bidSearchService.search(query).map(BidDto.DetailForm::new);
    }

    @GetMapping(value = "/user/{userId}")
    public List<BidDto.DetailForm> getUserBidList(@PathVariable long userId) {
        return bidInfoService.getUserBidList(userId).stream()
                .map(BidDto.DetailForm::new)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public BidDto.DetailForm getBidDetail(@PathVariable final long id) {
        return new BidDto.DetailForm(bidInfoService.getBidById(id));
    }

    @PutMapping(value = "/{id}")
    public BidDto.DetailForm updateBidInfo(@PathVariable final long id, @RequestBody final BidDto.UpdateReq dto) {
        return new BidDto.DetailForm(bidInfoService.updateBid(id, dto));
    }

    @PutMapping(value = "/revert/{id}")
    public BidDto.DetailForm revertBid(@PathVariable final long id) {
        return new BidDto.DetailForm(bidInfoService.revertBid(id));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteBid(@PathVariable final long id) {
        bidInfoService.deleteBidInfo(id);
    }
}
