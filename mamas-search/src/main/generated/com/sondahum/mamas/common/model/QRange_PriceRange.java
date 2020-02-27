package com.sondahum.mamas.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRange_PriceRange is a Querydsl query type for PriceRange
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QRange_PriceRange extends BeanPath<Range.PriceRange> {

    private static final long serialVersionUID = -1310353382L;

    public static final QRange_PriceRange priceRange = new QRange_PriceRange("priceRange");

    public final NumberPath<Long> maximum = createNumber("maximum", Long.class);

    public final NumberPath<Long> minimum = createNumber("minimum", Long.class);

    public QRange_PriceRange(String variable) {
        super(Range.PriceRange.class, forVariable(variable));
    }

    public QRange_PriceRange(Path<? extends Range.PriceRange> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRange_PriceRange(PathMetadata metadata) {
        super(Range.PriceRange.class, metadata);
    }

}

