package com.sondahum.mamas.domain.user;

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

    private static final long serialVersionUID = 1990160599L;

    public static final QUser user = new QUser("user");

    public final com.sondahum.mamas.common.model.QBaseEntity _super = new com.sondahum.mamas.common.model.QBaseEntity(this);

    public final ListPath<com.sondahum.mamas.domain.bid.Bid, com.sondahum.mamas.domain.bid.QBid> bidList = this.<com.sondahum.mamas.domain.bid.Bid, com.sondahum.mamas.domain.bid.QBid>createList("bidList", com.sondahum.mamas.domain.bid.Bid.class, com.sondahum.mamas.domain.bid.QBid.class, PathInits.DIRECT2);

    public final ListPath<com.sondahum.mamas.domain.contract.Contract, com.sondahum.mamas.domain.contract.QContract> contractList = this.<com.sondahum.mamas.domain.contract.Contract, com.sondahum.mamas.domain.contract.QContract>createList("contractList", com.sondahum.mamas.domain.contract.Contract.class, com.sondahum.mamas.domain.contract.QContract.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final ListPath<com.sondahum.mamas.domain.estate.Estate, com.sondahum.mamas.domain.estate.QEstate> estateList = this.<com.sondahum.mamas.domain.estate.Estate, com.sondahum.mamas.domain.estate.QEstate>createList("estateList", com.sondahum.mamas.domain.estate.Estate.class, com.sondahum.mamas.domain.estate.QEstate.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final StringPath phone = createString("phone");

    public final EnumPath<com.sondahum.mamas.domain.user.model.Role> role = createEnum("role", com.sondahum.mamas.domain.user.model.Role.class);

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

