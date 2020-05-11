package com.sondahum.mamas.common.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sondahum.mamas.common.config.SortDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.LinkedList;
import java.util.List;

public final class PageRequest {

    private int page;
    private int size;
    private List<Sort.Order> orderList;

    public PageRequest() {
        orderList = new LinkedList<>();
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
    public void setOrderList(List<Sort.Order> orderList) {
        this.orderList = orderList;
    }

    public void addOrder(Sort.Order order) {
        this.orderList.add(order);
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public List<Sort.Order> getOrderList() {
        return orderList;
    }

    public org.springframework.data.domain.PageRequest of() {
        if (orderList.isEmpty())
            return org.springframework.data.domain.PageRequest.of(page, size, Sort.Direction.DESC, "created_date");

        return org.springframework.data.domain.PageRequest.of(page, size, Sort.by(orderList));
    }
}
