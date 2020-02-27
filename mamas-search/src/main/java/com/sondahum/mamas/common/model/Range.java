package com.sondahum.mamas.common.model;

import lombok.*;

import javax.persistence.Embeddable;


public class Range {

    @Embeddable
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AreaRange {
        private Double minimum;
        private Double maximum;
    }

    @Embeddable
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PriceRange {
        private Long minimum;
        private Long maximum;
    }

}
