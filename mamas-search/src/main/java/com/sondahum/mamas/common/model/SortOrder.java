package com.sondahum.mamas.common.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SortOrder {
    String property;
    String direction;
}