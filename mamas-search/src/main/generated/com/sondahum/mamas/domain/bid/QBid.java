package com.sondahum.mamas.domain.bid;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import com.sondahum.mamas.domain.bid.model.Action;


/**
 * QBid is a Querydsl query type for Bid
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBid extends EntityPathBase<Bid> {

    private static final long serialVersionUID = 642438997L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBid bid = new QBid("bid");

    public final EnumPath<Action> action = createEnum("action", Action.class);

    public final DatePath<java.time.LocalDate> createdDate = createDate("createdDate", java.time.LocalDate.class);

    public final com.sondahum.mamas.domain.estate.QEstate estate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> modifiedDate = createDate("modifiedDate", java.time.LocalDate.class);

    public final com.sondahum.mamas.common.model.QRange_Price priceRange;

    public final com.sondahum.mamas.domain.user.QUser user;

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
        this.estate = inits.isInitialized("estate") ? new com.sondahum.mamas.domain.estate.QEstate(forProperty("estate"), inits.get("estate")) : null;
        this.priceRange = inits.isInitialized("priceRange") ? new com.sondahum.mamas.common.model.QRange_Price(forProperty("priceRange")) : null;
        this.user = inits.isInitialized("user") ? new com.sondahum.mamas.domain.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

