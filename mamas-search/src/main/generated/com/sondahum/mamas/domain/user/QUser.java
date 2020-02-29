package com.sondahum.mamas.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import com.sondahum.mamas.domain.user.model.Role;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1990160599L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final ListPath<com.sondahum.mamas.domain.bid.Bid, com.sondahum.mamas.domain.bid.QBid> bidList = this.<com.sondahum.mamas.domain.bid.Bid, com.sondahum.mamas.domain.bid.QBid>createList("bidList", com.sondahum.mamas.domain.bid.Bid.class, com.sondahum.mamas.domain.bid.QBid.class, PathInits.DIRECT2);

    public final ListPath<com.sondahum.mamas.domain.contract.Contract, com.sondahum.mamas.domain.contract.QContract> contractList = this.<com.sondahum.mamas.domain.contract.Contract, com.sondahum.mamas.domain.contract.QContract>createList("contractList", com.sondahum.mamas.domain.contract.Contract.class, com.sondahum.mamas.domain.contract.QContract.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> createdDate = createDate("createdDate", java.time.LocalDate.class);

    public final ListPath<com.sondahum.mamas.domain.estate.Estate, com.sondahum.mamas.domain.estate.QEstate> estateList = this.<com.sondahum.mamas.domain.estate.Estate, com.sondahum.mamas.domain.estate.QEstate>createList("estateList", com.sondahum.mamas.domain.estate.Estate.class, com.sondahum.mamas.domain.estate.QEstate.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> modifiedDate = createDate("modifiedDate", java.time.LocalDate.class);

    public final StringPath name = createString("name");

    public final QPhone phone;

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.phone = inits.isInitialized("phone") ? new QPhone(forProperty("phone")) : null;
    }

}

