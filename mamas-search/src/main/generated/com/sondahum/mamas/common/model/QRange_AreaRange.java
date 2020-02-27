package com.sondahum.mamas.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRange_AreaRange is a Querydsl query type for AreaRange
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QRange_AreaRange extends BeanPath<Range.AreaRange> {

    private static final long serialVersionUID = 30687562L;

    public static final QRange_AreaRange areaRange = new QRange_AreaRange("areaRange");

    public final NumberPath<Double> maximum = createNumber("maximum", Double.class);

    public final NumberPath<Double> minimum = createNumber("minimum", Double.class);

    public QRange_AreaRange(String variable) {
        super(Range.AreaRange.class, forVariable(variable));
    }

    public QRange_AreaRange(Path<? extends Range.AreaRange> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRange_AreaRange(PathMetadata metadata) {
        super(Range.AreaRange.class, metadata);
    }

}

