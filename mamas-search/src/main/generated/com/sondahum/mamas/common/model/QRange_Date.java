package com.sondahum.mamas.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRange_Date is a Querydsl query type for Date
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QRange_Date extends BeanPath<Range.Date> {

    private static final long serialVersionUID = 316997332L;

    public static final QRange_Date date = new QRange_Date("date");

    public final DateTimePath<java.time.LocalDateTime> maximum = createDateTime("maximum", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> minimum = createDateTime("minimum", java.time.LocalDateTime.class);

    public QRange_Date(String variable) {
        super(Range.Date.class, forVariable(variable));
    }

    public QRange_Date(Path<? extends Range.Date> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRange_Date(PathMetadata metadata) {
        super(Range.Date.class, metadata);
    }

}

