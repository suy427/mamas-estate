package com.sondahum.mamas.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPrice is a Querydsl query type for Price
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QPrice extends BeanPath<Range> {

    private static final long serialVersionUID = 1103056692L;

    public static final QPrice price = new QPrice("price");

    public final NumberPath<Long> maximum = createNumber("maximum", Long.class);

    public final NumberPath<Long> minimum = createNumber("minimum", Long.class);

    public QPrice(String variable) {
        super(Range.class, forVariable(variable));
    }

    public QPrice(Path<? extends Range> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPrice(PathMetadata metadata) {
        super(Range.class, metadata);
    }

}

