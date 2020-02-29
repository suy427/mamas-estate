package com.sondahum.mamas.domain.bid;


import com.sondahum.mamas.dto.BidDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import java.util.List;


import static com.sondahum.mamas.domain.bid.QBid.bid;


@Service
public class BidSearchService extends QuerydslRepositorySupport {

    public BidSearchService() {
        super(Bid.class);
    }

    public Page<Bid> search(final BidDto.SearchReq query, final Pageable pageable) {
        List<Bid> bids = from(bid).where().fetch();

        return new PageImpl<>(bids, pageable, bids.size());
    }
}
