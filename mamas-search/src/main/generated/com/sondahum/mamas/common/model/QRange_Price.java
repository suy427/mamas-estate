package com.sondahum.mamas.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRange_Price is a Querydsl query type for Price
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QRange_Price extends BeanPath<Range.Price> {

    private static final long serialVersionUID = 1248560867L;

    public static final QRange_Price price = new QRange_Price("price");

    public final NumberPath<Long> maximum = createNumber("maximum", Long.class);

    public final NumberPath<Long> minimum = createNumber("minimum", Long.class);

    public QRange_Price(String variable) {
        super(Range.Price.class, forVariable(variable));
    }

    public QRange_Price(Path<? extends Range.Price> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRange_Price(PathMetadata metadata) {
        super(Range.Price.class, metadata);
    }

}

