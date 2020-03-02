package com.sondahum.mamas.common.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDate;



public class Range {

    @Embeddable
    @Getter
    @Setter
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
    }


    @Embeddable
    @Getter
    @Setter
    public static class Date {
        private LocalDate minimum;
        private LocalDate maximum;

        public boolean isIn(Range.Date range) {
            LocalDate from = range.minimum;
            LocalDate to = range.maximum;

            return from.compareTo(minimum) <= 0 && maximum.compareTo(to) <= 0;
        }

        public boolean isIn(LocalDate data) {
               return minimum.compareTo(data) <= 0 && maximum.compareTo(data) >= 0;
        }
    }


    @Embeddable
    @Getter
    @Setter
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
    }
}
