package com.sondahum.mamas.common.model;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Price {
    private Long minimum;
    private Long maximum;
}
