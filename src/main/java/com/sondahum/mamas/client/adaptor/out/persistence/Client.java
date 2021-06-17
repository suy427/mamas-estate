package com.sondahum.mamas.client.adaptor.out.persistence;

import com.sondahum.mamas.bid.adaptor.out.persistence.Bid;
import com.sondahum.mamas.contract.adaptor.out.persistence.Contract;
import com.sondahum.mamas.estate.adaptor.out.persistence.Estate;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue
    public Long id;

    @Column
    public String name;

    @Column
    public String phone;

    @Column
    public Instant createdAt;

    @Column
    public Instant updatedAt;

    @OneToMany
    public List<Bid> bidHistories = new ArrayList<>();

    @OneToMany
    public List<Estate> possessions = new ArrayList<>();

    @OneToMany
    public List<Contract> contractHistories = new ArrayList<>();
}