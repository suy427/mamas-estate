package com.sondahum.mamas.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.sondahum.mamas.domain.user.model.Phone;


/**
 * QPhone is a Querydsl query type for Phone
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QPhone extends BeanPath<Phone> {

    private static final long serialVersionUID = 1560500706L;

    public static final QPhone phone = new QPhone("phone");

    public final StringPath first = createString("first");

    public final StringPath last = createString("last");

    public final StringPath middle = createString("middle");

    public final StringPath wholeNumber = createString("wholeNumber");

    public QPhone(String variable) {
        super(Phone.class, forVariable(variable));
    }

    public QPhone(Path<? extends Phone> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPhone(PathMetadata metadata) {
        super(Phone.class, metadata);
    }

}

