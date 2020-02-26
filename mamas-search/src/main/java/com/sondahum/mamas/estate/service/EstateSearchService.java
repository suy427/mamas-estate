package com.sondahum.mamas.estate.service;

import com.querydsl.jpa.JPQLQuery;
import com.sondahum.mamas.estate.domain.Estate;
import com.sondahum.mamas.estate.domain.EstateSearchFilter;
import com.sondahum.mamas.estate.domain.QEstate;
import com.sondahum.mamas.user.domain.UserSearchFilter;
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

    public Page<Estate> search(final EstateSearchFilter filter, final String value, final Pageable pageable) {
        final QEstate estate = QEstate.estate;
        final JPQLQuery<Estate> query;

        switch (filter) {
            case NAME: // 이름에 value가 포함되는지. 성, 이름, 전체이름으로 가능. --> TODO 성으로 찾는건 좀 다를수도 있다..!
                query = from(estate)
                        .where(estate.name.likeIgnoreCase(value + "%"));
                break;
            case ADDRESS:
                query = from(estate)
                        .where(estate.address.address1.likeIgnoreCase(value + "%")
                            .or(estate.address.address2.likeIgnoreCase(value + "%"))
                            .or(estate.address.address3.likeIgnoreCase(value + "%"))
                        );
                break;
            case OWNER:
                query = from(estate)
                        .where(estate.owner.name.likeIgnoreCase(value + "%"));
                break;
            case ALL:
                query = from(estate).fetchAll();
                break;
            default:
                throw new IllegalArgumentException();
        }
        final List<Estate> userList = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(userList, pageable, query.fetchCount());
    }

}
