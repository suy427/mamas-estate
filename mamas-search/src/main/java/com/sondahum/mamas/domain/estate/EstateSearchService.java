package com.sondahum.mamas.domain.estate;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.domain.estate.model.Address;
import com.sondahum.mamas.domain.estate.model.Status;
import com.sondahum.mamas.dto.EstateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.sondahum.mamas.domain.estate.QEstate.estate;


@Service
public class EstateSearchService extends QuerydslRepositorySupport {

    public EstateSearchService() {
        super(Estate.class);
    }

    public Page<Estate> search(final EstateDto.SearchReq query, final Pageable pageable) {
        List<Estate> estates = from(estate).where(
                name(query.getName())
                , address(query.getAddress())
                , area(query.getArea())
                , status(query.getStatus())
                , ownerRequiredPrice(query.getOwnerRequirePriceRange())
                , marketPriceRange(query.getMarketPriceRange())
                , owner(query.getOwner())
        ).fetch();


        return new PageImpl<>(estates, pageable, estates.size());
    }

    private BooleanExpression name(String name) {
        if (StringUtils.isEmpty(name)) return null;

        return estate.name.likeIgnoreCase("%"+name+"%");
    }

    private BooleanExpression address(String address) {
        if (address == null) return null;

        return estate.address.address1.likeIgnoreCase("%"+address+"%")
        .or(estate.address.address2.likeIgnoreCase("%"+address+"%"))
        .or(estate.address.address3.likeIgnoreCase("%"+address+"%"));
    }

    private BooleanExpression area(Range.Area areaRange) {
        if (areaRange == null) return null;

        return estate.area.between(areaRange.getMaximum()-Double.MIN_VALUE, areaRange.getMaximum()+Double.MIN_VALUE);
    }

    private BooleanExpression status(Status status) {
        if (status == null) return null;

        return estate.status.eq(status);
    }

    private BooleanExpression ownerRequiredPrice(Range.Price ownerPrice) {
        return PriceRangeContition(ownerPrice);
    }

    private BooleanExpression marketPriceRange(Range.Price marketPrice) {
        return PriceRangeContition(marketPrice);
    }

    private BooleanExpression PriceRangeContition(Range.Price priceRange) {
        if (priceRange == null) return null;

        if (priceRange.getMaximum().equals(priceRange.getMinimum())) {
            Long price = priceRange.getMaximum();
            return estate.ownerRequirePriceRange.minimum.loe(price)
                    .and(estate.ownerRequirePriceRange.maximum.goe(price));
        } else {
            return estate.ownerRequirePriceRange.minimum.goe(priceRange.getMinimum())
                    .and(estate.ownerRequirePriceRange.maximum.loe(priceRange.getMaximum()));
        }
    }

    private BooleanExpression owner(String name) {
        if (StringUtils.isEmpty(name)) return null;

        return estate.owner.name.likeIgnoreCase("%"+name+"%");
    }
}
