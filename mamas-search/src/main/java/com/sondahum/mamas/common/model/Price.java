package com.sondahum.mamas.common.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class Price {
    private Long minimum;
    private Long maximum;
}
