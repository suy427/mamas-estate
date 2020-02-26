package com.sondahum.mamas.estate.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEstate is a Querydsl query type for Estate
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEstate extends EntityPathBase<Estate> {

    private static final long serialVersionUID = -1656959865L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEstate estate = new QEstate("estate");

    public final com.sondahum.mamas.common.model.QAddress address;

    public final NumberPath<Double> area = createNumber("area", Double.class);

    public final ListPath<com.sondahum.mamas.bid.domain.Bid, com.sondahum.mamas.bid.domain.QBid> bidList = this.<com.sondahum.mamas.bid.domain.Bid, com.sondahum.mamas.bid.domain.QBid>createList("bidList", com.sondahum.mamas.bid.domain.Bid.class, com.sondahum.mamas.bid.domain.QBid.class, PathInits.DIRECT2);

    public final EnumPath<ContractType> contractType = createEnum("contractType", ContractType.class);

    public final DatePath<java.time.LocalDate> createdDate = createDate("createdDate", java.time.LocalDate.class);

    public final EnumPath<EstateType> estateType = createEnum("estateType", EstateType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.sondahum.mamas.common.model.QPrice marketPriceRange;

    public final DatePath<java.time.LocalDate> modifiedDate = createDate("modifiedDate", java.time.LocalDate.class);

    public final StringPath name = createString("name");

    public final com.sondahum.mamas.user.domain.QUser owner;

    public final com.sondahum.mamas.common.model.QPrice ownerRequirePriceRange;

    public final EnumPath<Status> status = createEnum("status", Status.class);

    public QEstate(String variable) {
        this(Estate.class, forVariable(variable), INITS);
    }

    public QEstate(Path<? extends Estate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEstate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEstate(PathMetadata metadata, PathInits inits) {
        this(Estate.class, metadata, inits);
    }

    public QEstate(Class<? extends Estate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new com.sondahum.mamas.common.model.QAddress(forProperty("address")) : null;
        this.marketPriceRange = inits.isInitialized("marketPriceRange") ? new com.sondahum.mamas.common.model.QPrice(forProperty("marketPriceRange")) : null;
        this.owner = inits.isInitialized("owner") ? new com.sondahum.mamas.user.domain.QUser(forProperty("owner"), inits.get("owner")) : null;
        this.ownerRequirePriceRange = inits.isInitialized("ownerRequirePriceRange") ? new com.sondahum.mamas.common.model.QPrice(forProperty("ownerRequirePriceRange")) : null;
    }

}

