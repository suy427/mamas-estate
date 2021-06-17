package com.sondahum.mamas.bid.adaptor.out.persistence;

import com.sondahum.mamas.client.adaptor.out.persistence.Client;
import com.sondahum.mamas.estate.adaptor.out.persistence.Estate;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity
@Table(name = "bid")
public class Bid {
    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    @JoinColumn(name = "client", nullable = false)
    public Client bidder;

    @ManyToOne
    public Estate estate;

    @Column
    public BigDecimal price;

    @Column
    public Instant createdAt;

    @Column
    public Instant updatedAt;
}
