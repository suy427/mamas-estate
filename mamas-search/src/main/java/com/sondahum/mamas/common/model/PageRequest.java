package com.sondahum.mamas.common.model;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;


public final class PageRequest {

    private int page;
    private int size;
    private List<SortOrder> sort;

    public PageRequest() {
    }

    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    public void setSize(int size) {
        int DEFAULT_SIZE = 10;
        int MAX_SIZE = 50;
        this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
    }

    public void setSort(List<SortOrder> sort) {
        this.sort = sort;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public List<SortOrder> getSort() {
        return sort;
    }

    private Sort parseSort() {
        List<Sort.Order> orders = new ArrayList<>();

        sort.forEach((order) ->
                orders.add(new Sort.Order(Sort.Direction.fromString(order.direction), order.property))
        );

        return Sort.by(orders);
    }

    public org.springframework.data.domain.PageRequest of() {
        return org.springframework.data.domain.PageRequest.of(page, size, parseSort());
    }
}
