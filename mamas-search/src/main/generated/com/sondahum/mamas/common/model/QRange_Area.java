package com.sondahum.mamas.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRange_Area is a Querydsl query type for Area
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QRange_Area extends BeanPath<Range.Area> {

    private static final long serialVersionUID = 316923827L;

    public static final QRange_Area area = new QRange_Area("area");

    public final NumberPath<Double> maximum = createNumber("maximum", Double.class);

    public final NumberPath<Double> minimum = createNumber("minimum", Double.class);

    public QRange_Area(String variable) {
        super(Range.Area.class, forVariable(variable));
    }

    public QRange_Area(Path<? extends Range.Area> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRange_Area(PathMetadata metadata) {
        super(Range.Area.class, metadata);
    }

}

