package com.sondahum.mamas.bid.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBid is a Querydsl query type for Bid
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBid extends EntityPathBase<Bid> {

    private static final long serialVersionUID = 127181911L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBid bid = new QBid("bid");

    public final EnumPath<Action> action = createEnum("action", Action.class);

    public final DatePath<java.time.LocalDate> createdDate = createDate("createdDate", java.time.LocalDate.class);

    public final com.sondahum.mamas.estate.domain.QEstate estate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> modifiedDate = createDate("modifiedDate", java.time.LocalDate.class);

    public final com.sondahum.mamas.common.model.QPrice priceRange;

    public final com.sondahum.mamas.user.domain.QUser user;

    public QBid(String variable) {
        this(Bid.class, forVariable(variable), INITS);
    }

    public QBid(Path<? extends Bid> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBid(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBid(PathMetadata metadata, PathInits inits) {
        this(Bid.class, metadata, inits);
    }

    public QBid(Class<? extends Bid> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.estate = inits.isInitialized("estate") ? new com.sondahum.mamas.estate.domain.QEstate(forProperty("estate"), inits.get("estate")) : null;
        this.priceRange = inits.isInitialized("priceRange") ? new com.sondahum.mamas.common.model.QPrice(forProperty("priceRange")) : null;
        this.user = inits.isInitialized("user") ? new com.sondahum.mamas.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

