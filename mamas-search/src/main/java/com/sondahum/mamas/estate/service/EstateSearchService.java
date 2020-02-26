package com.sondahum.mamas.estate.service;

import com.querydsl.jpa.JPQLQuery;
import com.sondahum.mamas.estate.domain.SearchQuery;
import com.sondahum.mamas.estate.domain.Estate;
import com.sondahum.mamas.estate.domain.EstateSearchFilter;
import com.sondahum.mamas.estate.domain.QEstate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class EstateSearchService extends QuerydslRepositorySupport {

    EstateSearchService() {
        super(Estate.class);
    }
    // todo 정렬기능도 넣자..
    public Page<Estate> search(final EstateSearchFilter filter, final SearchQuery query, final Pageable pageable) {
        final QEstate estate = QEstate.estate;
        final JPQLQuery<Estate> sql;

        switch (filter) { // todo 이걸 if else 로 바꾸고 BooleanBuilder를 쓰면 여러 필터 함께 사용 가능하다.
            case NAME: // 이름에 value가 포함되는지. 성, 이름, 전체이름으로 가능. --> TODO 성으로 찾는건 좀 다를수도 있다..!
                sql = from(estate)
                        .where(estate.name.likeIgnoreCase("%" + query + "%"));
                break;
            case ADDRESS:
                sql = from(estate)
                        .where(estate.address.address1.likeIgnoreCase("%" + query.getStringQuery() + "%")
                                .or(estate.address.address2.likeIgnoreCase("%" + query.getStringQuery() + "%"))
                                .or(estate.address.address3.likeIgnoreCase("%" + query.getStringQuery() + "%")));
                break;
            case AREA:
                sql = from(estate)
                        .where(estate.area.goe(query.getRangeQuery().getMinimum())
                        .and(estate.area.loe(query.getRangeQuery().getMaximum())));
                break;
            case STATUS:
                sql = from(estate)
                        .where(estate.status.stringValue().eq(query.getStringQuery()));
                break;
            case OWNER_REQUIRED_PRICE: // TODO 쿼리의 종류가 단순 String 이상이여야한다...
                sql = from(estate)
                        .where(estate.ownerRequirePriceRange.minimum.goe(query.getRangeQuery().getMinimum()) // todo loe'All'은 뭐지..?
                                .and(estate.ownerRequirePriceRange.maximum.loe(query.getRangeQuery().getMaximum())));
                break;
            case MARKET_PRICE:
                sql = from(estate)
                        .where(estate.marketPriceRange.minimum.goe(query.getRangeQuery().getMinimum())
                                .and(estate.marketPriceRange.maximum.loe(query.getRangeQuery().getMaximum())));
                break;
            case OWNER:
                sql = from(estate)
                        .where(estate.owner.name.likeIgnoreCase("%" + query + "%"));
                break;
            case ALL:
                sql = from(estate).fetchAll();
                break;
            default:
                throw new IllegalArgumentException();
        }
        final List<Estate> userList = getQuerydsl().applyPagination(pageable, sql).fetch();
        return new PageImpl<>(userList, pageable, sql.fetchCount());
    }

}
