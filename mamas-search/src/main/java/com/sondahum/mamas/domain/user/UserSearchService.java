package com.sondahum.mamas.domain.user;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Projection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.sondahum.mamas.domain.bid.QBid.bid;
import static com.sondahum.mamas.domain.contract.QContract.contract;
import static com.sondahum.mamas.domain.estate.QEstate.estate;
import static com.sondahum.mamas.domain.user.QUser.user;



@Service
@Slf4j
public class UserSearchService extends QuerydslRepositorySupport {

    private final UserRepository userRepository;


    public UserSearchService(UserRepository userRepository) {
        super(User.class);
        this.userRepository = userRepository;
    }

    public List<User> specify(String query) {
        if (query == null)  return null;

        return from(user).where(
                validity()
                , name(query)
                , phone(query)
        ).fetch();
    }

    public Page<User> search(final UserDto.SearchReq query, final Pageable pageable) {
        if (query == null) {
            return userRepository.findAll(pageable);
        }

        List<User> users = from(user)
                .innerJoin(user.bidList)
                .innerJoin(user.contractList)
                .on(
                        bid.createdDate
                        .between(query.getBidDate().getMinimum(), query.getBidDate().getMaximum())
                )
                .on(
                        contract.contractedDate
                                .between(query.getContractDate().getMinimum(), query.getContractDate().getMaximum())
                )
                .where(
                    validity()
                    , name(query.getName())
                    , phone(query.getPhone())
                    , role(query.getRole().name())
                )
                .fetchJoin().fetch();

//        bidDate(users, query.getBidDate());           // todo 이 두 method 반드시 refactoring 할 것!!!!
//        contractDate(users, query.getContractDate()); // todo 이 두 method 반드시 refactoring 할 것!!!!

        return new PageImpl<>(users, pageable, users.size());
    }

    private BooleanExpression validity() {
        return user.active.eq(true);
    }

    private BooleanExpression name(String name) {
        if (StringUtils.isEmpty(name))
            return null;

        return user.name.likeIgnoreCase("%"+name+"%");
    }

    private BooleanExpression phone(String phone) {
        if (StringUtils.isEmpty(phone))
            return null;

        return user.phone.likeIgnoreCase("%"+phone+"%");
    }

    private BooleanExpression role(String role) {
        if (StringUtils.isEmpty(role))
            return null;

        return user.role.stringValue().eq(role);
    }

//    private void bidDate(List<User> users, Range.Date date) {
//        if (date == null)
//            return;
//
//        for (User user : users) {
//            user.setBidList(user.getBidList().stream()
//                    .filter(bid ->
//                            date.isIn(bid.getCreatedDate())
//                    ).collect(Collectors.toList()));
//        }
//    }

//    private void contractDate(List<User> users, Range.Date date) {
//        if (date == null)
//            return;
//
//        for (User user : users) {
//            user.setContractList(user.getContractList().stream()
//                    .filter(contract ->
//                            date.isIn(contract.getCreatedDate())
//                    ).collect(Collectors.toList()));
//        }
//    }
}
