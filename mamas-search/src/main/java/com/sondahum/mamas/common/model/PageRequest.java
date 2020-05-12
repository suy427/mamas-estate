package com.sondahum.mamas.common.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sondahum.mamas.common.config.SortDeserializer;
import com.sondahum.mamas.common.config.SortSerializer;
import org.springframework.data.domain.Sort;


public final class PageRequest {

    private int page;
    private int size;
    @JsonSerialize(using = SortSerializer.class)
    private Sort sort;

    public PageRequest() {
        Sort.Order defaultOrder = new Sort.Order(Sort.Direction.DESC, "created_date");
        sort = Sort.by(defaultOrder);
    }

    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    public void setSize(int size) {
        int DEFAULT_SIZE = 10;
        int MAX_SIZE = 50;
        this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
    }

    @JsonDeserialize(using = SortDeserializer.class)
    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public Sort getSort() {
        return sort;
    }

    public org.springframework.data.domain.PageRequest of() {
        return org.springframework.data.domain.PageRequest.of(page, size, sort);
    }
}
