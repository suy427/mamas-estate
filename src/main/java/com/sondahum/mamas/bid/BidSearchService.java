package com.sondahum.mamas.bid;


import com.sondahum.mamas.bid.adaptor.out.persistence.Bid;
import com.sondahum.mamas.bid.adaptor.out.persistence.BidRepository;
import com.sondahum.mamas.dto.BidDto;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


@Service
public class BidSearchService {
    private final BidRepository bidRepository;

    public BidSearchService(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    public Page<Bid> search(final BidDto.SearchReq query) {
        return null;
    }
}
