package com.sondahum.mamas.domain.bid;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.dto.BidDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


import static com.sondahum.mamas.domain.bid.QBid.bid;


@Service
public class BidSearchService extends QuerydslRepositorySupport {

    private final BidRepository bidRepository;

    public BidSearchService(BidRepository bidRepository) {
        super(Bid.class);
        this.bidRepository = bidRepository;
    }

    public Page<Bid> search(final BidDto.SearchReq query, final Pageable pageable) {
        if (query == null) {
            bidRepository.findAll(pageable);
        }

        List<Bid> bids = from(bid).where(
                user(query.getUser())       // todo 얘 왜이러지... 다른덴 안이런데.. null 들어오면 안되는건가.. method내에서 check하긴하는데;;
                , estate(query.getEstate())
                , action(query.getAction())
                , date(query.getDate())
                , price(query.getPrice())
        ).fetch();

        return new PageImpl<>(bids, pageable, bids.size());
    }

    private BooleanExpression user(String user) {
        if (StringUtils.isEmpty(user)) return null;

        return bid.user.name.likeIgnoreCase("%"+user+"%");
    }

    private BooleanExpression estate(String estate) {
        if (StringUtils.isEmpty(estate)) return null;

        return bid.estate.name.likeIgnoreCase("%"+estate+"%");
    }

    private BooleanExpression action(Action action) {
        if (action == null) return null;

        return bid.action.eq(action);
    }

    private BooleanExpression date(Range.Date dateRange) {
        if (dateRange == null) return null;

        return bid.createdDate.between(dateRange.getMinimum(), dateRange.getMaximum());
    }

    private BooleanExpression price(Range.Price priceRange) {
        if (priceRange == null) return null;

        if (priceRange.getMinimum().equals(priceRange.getMaximum())) {
            Long price = priceRange.getMinimum();

            return bid.priceRange.minimum.loe(price)
                    .and(bid.priceRange.maximum.goe(price));
        } else {
            return bid.priceRange.minimum.goe(priceRange.getMinimum())
                    .and(bid.priceRange.maximum.loe(priceRange.getMaximum()));
        }
    }
}
