package com.sondahum.mamas.domain.contract;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContract is a Querydsl query type for Contract
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QContract extends EntityPathBase<Contract> {

    private static final long serialVersionUID = -790114761L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContract contract = new QContract("contract");

    public final com.sondahum.mamas.domain.user.QUser buyer;

    public final DatePath<java.time.LocalDate> createdDate = createDate("createdDate", java.time.LocalDate.class);

    public final com.sondahum.mamas.domain.estate.QEstate estate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> modifiedDate = createDate("modifiedDate", java.time.LocalDate.class);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final com.sondahum.mamas.domain.user.QUser seller;

    public QContract(String variable) {
        this(Contract.class, forVariable(variable), INITS);
    }

    public QContract(Path<? extends Contract> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContract(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContract(PathMetadata metadata, PathInits inits) {
        this(Contract.class, metadata, inits);
    }

    public QContract(Class<? extends Contract> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.buyer = inits.isInitialized("buyer") ? new com.sondahum.mamas.domain.user.QUser(forProperty("buyer"), inits.get("buyer")) : null;
        this.estate = inits.isInitialized("estate") ? new com.sondahum.mamas.domain.estate.QEstate(forProperty("estate"), inits.get("estate")) : null;
        this.seller = inits.isInitialized("seller") ? new com.sondahum.mamas.domain.user.QUser(forProperty("seller"), inits.get("seller")) : null;
    }

}

