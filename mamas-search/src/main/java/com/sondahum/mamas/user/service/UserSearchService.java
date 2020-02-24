package com.sondahum.mamas.user.service;

import com.querydsl.jpa.JPQLQuery;
import com.sondahum.mamas.user.domain.SearchFilter;
import com.sondahum.mamas.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserSearchService extends QuerydslRepositorySupport {

    UserSearchService() {
        super(User.class);
    }

    public Page<User> search(final SearchFilter filter, final String value, final Pageable pageable) {
        final QUser user = QUser.user;
        final JPQLQuery<User> query;

        switch (filter) {
            case SearchFilter.NAME:
                query = from(user)
                        .where(user.name.likeIgnoreCase(value + "%"))
                break;
            case SearchFilter.PHONE:
                query = from(user)
                        .where(user.email.value.likeIgnoreCase(value + "%"));
                break;
            case SearchFilter.ROLE:
                query = from(user)
                        .where(user.role.likeIgnoreCase(value + "%"))
                break;
            case SearchFilter.ALL:
                query = from(user).fetchAll();
                break;
            default:
                throw new IllegalArgumentException();
        }
        final List<User> userList = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(userList, pageable, query.fetchCount());
    }
}