package com.sondahum.mamas.common.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDate;


@Embeddable
@Getter
@Setter
public class Range<T> {

    private T minimum;
    private T maximum;
}
