package com.sondahum.mamas.contract.adaptor.out.persistence;

import com.sondahum.mamas.client.adaptor.out.persistence.Client;
import com.sondahum.mamas.estate.adaptor.out.persistence.Estate;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity
@Table(name = "contract")
public class Contract {
    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    @JoinColumn(name = "seller", nullable = false)
    public Client seller;

    @ManyToOne
    @JoinColumn(name = "buyer", nullable = false)
    public Client buyer;

    @Column
    public BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "estate", nullable = false)
    public Estate estate;

    @Column
    public Instant contractedDate;

    @Column
    public Instant expireDate;

    @Column
    public Instant createdAt;

    @Column
    public Instant updatedAt;
}
