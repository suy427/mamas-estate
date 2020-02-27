package com.sondahum.mamas.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRange_DateRange is a Querydsl query type for DateRange
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QRange_DateRange extends BeanPath<Range.DateRange> {

    private static final long serialVersionUID = -117543223L;

    public static final QRange_DateRange dateRange = new QRange_DateRange("dateRange");

    public final DatePath<java.time.LocalDate> maximum = createDate("maximum", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> minimum = createDate("minimum", java.time.LocalDate.class);

    public QRange_DateRange(String variable) {
        super(Range.DateRange.class, forVariable(variable));
    }

    public QRange_DateRange(Path<? extends Range.DateRange> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRange_DateRange(PathMetadata metadata) {
        super(Range.DateRange.class, metadata);
    }

}

