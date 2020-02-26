package com.sondahum.mamas.estate.domain;

import com.sondahum.mamas.common.model.Range;
import lombok.Getter;

@Getter
public class SearchQuery {
    private String stringQuery;
    private Range rangeQuery;
}
