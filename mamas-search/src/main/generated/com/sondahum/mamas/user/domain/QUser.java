package com.sondahum.mamas.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -317422971L;

    public static final QUser user = new QUser("user");

    public final ListPath<com.sondahum.mamas.bid.domain.Bid, com.sondahum.mamas.bid.domain.QBid> bidList = this.<com.sondahum.mamas.bid.domain.Bid, com.sondahum.mamas.bid.domain.QBid>createList("bidList", com.sondahum.mamas.bid.domain.Bid.class, com.sondahum.mamas.bid.domain.QBid.class, PathInits.DIRECT2);

    public final ListPath<com.sondahum.mamas.contract.domain.Contract, com.sondahum.mamas.contract.domain.QContract> boughtList = this.<com.sondahum.mamas.contract.domain.Contract, com.sondahum.mamas.contract.domain.QContract>createList("boughtList", com.sondahum.mamas.contract.domain.Contract.class, com.sondahum.mamas.contract.domain.QContract.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> createdDate = createDate("createdDate", java.time.LocalDate.class);

    public final ListPath<com.sondahum.mamas.estate.domain.Estate, com.sondahum.mamas.estate.domain.QEstate> estateList = this.<com.sondahum.mamas.estate.domain.Estate, com.sondahum.mamas.estate.domain.QEstate>createList("estateList", com.sondahum.mamas.estate.domain.Estate.class, com.sondahum.mamas.estate.domain.QEstate.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> modifiedDate = createDate("modifiedDate", java.time.LocalDate.class);

    public final StringPath name = createString("name");

    public final StringPath phone = createString("phone");

    public final EnumPath<com.sondahum.mamas.common.model.Role> role = createEnum("role", com.sondahum.mamas.common.model.Role.class);

    public final ListPath<com.sondahum.mamas.contract.domain.Contract, com.sondahum.mamas.contract.domain.QContract> soldList = this.<com.sondahum.mamas.contract.domain.Contract, com.sondahum.mamas.contract.domain.QContract>createList("soldList", com.sondahum.mamas.contract.domain.Contract.class, com.sondahum.mamas.contract.domain.QContract.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}
