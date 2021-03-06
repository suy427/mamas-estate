package com.sondahum.mamas.domain.estate;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.domain.estate.model.EstateStatus;
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

    private final EstateRepository estateRepository;

    public EstateSearchService(EstateRepository estateRepository) {
        super(Estate.class);
        this.estateRepository = estateRepository;
    }

    public List<Estate> specify(String query) {
        if (query == null)  return null;

        return from(estate).where(
                validity()
                , name(query)
                , address(query)
                , owner(query)
        ).fetch();
    }

    public Page<Estate> search(final EstateDto.SearchReq query, final Pageable pageable) {
        if (query == null) {
            return estateRepository.findAll(pageable);
        }

        List<Estate> estates = from(estate).where(
                validity()
                , name(query.getName())
                , address(query.getAddress())
                , area(query.getArea())
                , status(query.getStatus())
                , ownerRequiredPrice(query.getOwnerRequirePriceRange())
                , marketPriceRange(query.getMarketPriceRange())
                , owner(query.getOwner())
        ).fetch();


        return new PageImpl<>(estates, pageable, estates.size());
    }

    private BooleanExpression validity() {
        return estate.active.eq(true);
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

        return estate.area.between(areaRange.getMinimum(), areaRange.getMaximum());
    }

    private BooleanExpression status(EstateStatus status) {
        if (status == null) return null;

        return estate.status.eq(status);
    }

    private BooleanExpression ownerRequiredPrice(Range.Price ownerPrice) {
        return PriceRangeCondition(ownerPrice);
    }

    private BooleanExpression marketPriceRange(Range.Price marketPrice) {
        return PriceRangeCondition(marketPrice);
    }

    private BooleanExpression PriceRangeCondition(Range.Price priceRange) {
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

    private BooleanExpression owner(String name) { // todo 이거도 join하면 좋은가..?
        if (StringUtils.isEmpty(name)) return null;

        return estate.owner.name.likeIgnoreCase("%"+name+"%");
    }
}
