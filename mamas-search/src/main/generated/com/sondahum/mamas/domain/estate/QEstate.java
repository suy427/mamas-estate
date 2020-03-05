package com.sondahum.mamas.domain.estate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import com.sondahum.mamas.domain.estate.model.EstateStatus;


/**
 * QEstate is a Querydsl query type for Estate
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEstate extends EntityPathBase<Estate> {

    private static final long serialVersionUID = 1844913271L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEstate estate = new QEstate("estate");

    public final com.sondahum.mamas.common.model.QBaseEntity _super = new com.sondahum.mamas.common.model.QBaseEntity(this);

    public final com.sondahum.mamas.domain.estate.model.QAddress address;

    public final NumberPath<Double> area = createNumber("area", Double.class);

    public final ListPath<com.sondahum.mamas.domain.bid.Bid, com.sondahum.mamas.domain.bid.QBid> bidList = this.<com.sondahum.mamas.domain.bid.Bid, com.sondahum.mamas.domain.bid.QBid>createList("bidList", com.sondahum.mamas.domain.bid.Bid.class, com.sondahum.mamas.domain.bid.QBid.class, PathInits.DIRECT2);

    public final EnumPath<com.sondahum.mamas.domain.estate.model.ContractType> contractType = createEnum("contractType", com.sondahum.mamas.domain.estate.model.ContractType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final EnumPath<com.sondahum.mamas.domain.estate.model.EstateType> estateType = createEnum("estateType", com.sondahum.mamas.domain.estate.model.EstateType.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final com.sondahum.mamas.common.model.QRange_Price marketPriceRange;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final com.sondahum.mamas.domain.user.QUser owner;

    public final com.sondahum.mamas.common.model.QRange_Price ownerRequirePriceRange;

    public final DateTimePath<java.time.LocalDateTime> registeredDate = createDateTime("registeredDate", java.time.LocalDateTime.class);

    public final EnumPath<EstateStatus> status = createEnum("status", EstateStatus.class);

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
        this.address = inits.isInitialized("address") ? new com.sondahum.mamas.domain.estate.model.QAddress(forProperty("address")) : null;
        this.marketPriceRange = inits.isInitialized("marketPriceRange") ? new com.sondahum.mamas.common.model.QRange_Price(forProperty("marketPriceRange")) : null;
        this.owner = inits.isInitialized("owner") ? new com.sondahum.mamas.domain.user.QUser(forProperty("owner"), inits.get("owner")) : null;
        this.ownerRequirePriceRange = inits.isInitialized("ownerRequirePriceRange") ? new com.sondahum.mamas.common.model.QRange_Price(forProperty("ownerRequirePriceRange")) : null;
    }

}

