package com.sondahum.mamas.common.model;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Range {
    private Long minimum;
    private Long maximum;
}
