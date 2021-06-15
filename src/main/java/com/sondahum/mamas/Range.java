package com.sondahum.mamas;

import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;


public class Range {

    @Embeddable
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Price {
        private Long minimum;
        private Long maximum;

        public boolean isIn(Range.Price range) {
            Long from = range.minimum;
            Long to = range.maximum;

            return from <= minimum && maximum <= to;
        }

        public boolean isIn(Long price) {
            return minimum <= price && price <= maximum;
        }

        @Override
        public String toString() {
            return minimum+" ~ "+maximum;
        }
    }


    @Getter
    @Setter
    @Embeddable
    public static class Date {
        private LocalDateTime minimum;
        private LocalDateTime maximum;

        public boolean isIn(Range.Date range) {
            LocalDateTime from = range.minimum;
            LocalDateTime to = range.maximum;

            return from.compareTo(minimum) <= 0 && maximum.compareTo(to) <= 0;
        }

        public boolean isIn(LocalDateTime date) {
               return minimum.compareTo(date) <= 0 && maximum.compareTo(date) >= 0;
        }

        @Override
        public String toString() {
            return minimum+" ~ "+maximum;
        }
    }


    @Getter
    @Setter
    @Embeddable
    public static class Area {
        private Double minimum;
        private Double maximum;

        public boolean isIn(Range.Area range) {
            Double from = range.minimum;
            Double to = range.maximum;

            return from <= minimum && maximum <= to;
        }

        public boolean isIn(Double area) {
            return minimum <= area && area <= maximum;
        }

        @Override
        public String toString() {
            return minimum+" ~ "+maximum;
        }
    }
}
