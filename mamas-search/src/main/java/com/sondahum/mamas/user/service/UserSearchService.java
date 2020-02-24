package com.sondahum.mamas.user.service;

import com.querydsl.jpa.JPQLQuery;
import com.sondahum.mamas.user.domain.QUser;
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
            case NAME: // 이름에 value가 포함되는지. 성, 이름, 전체이름으로 가능. --> TODO 성으로 찾는건 좀 다를수도 있다..!
                query = from(user)
                        .where(user.name.likeIgnoreCase(value + "%"));
                break;
            case PHONE:
                query = from(user)
                        .where(user.phone.likeIgnoreCase(value + "%"));
                break;
            case ROLE:
                query = from(user)
                        .where(user.role.stringValue().likeIgnoreCase(value + "%"));
                break;
            case ALL:
                query = from(user).fetchAll();
                break;
            default:
                throw new IllegalArgumentException();
        }
        final List<User> userList = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(userList, pageable, query.fetchCount());
    }
}