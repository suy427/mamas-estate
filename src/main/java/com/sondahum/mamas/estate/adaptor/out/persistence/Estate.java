package com.sondahum.mamas.estate.adaptor.out.persistence;

import com.sondahum.mamas.bid.adaptor.out.persistence.Bid;
import com.sondahum.mamas.client.adaptor.out.persistence.Client;
import com.sondahum.mamas.contract.adaptor.out.persistence.Contract;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "estate")
public class Estate {
    @Id
    @GeneratedValue
    public Long id;

    public String name;

    @Column
    public String address1;

    @Column
    public String address2;

    @Column
    public String address3;

    @Column
    public BigDecimal area;

    @Column
    public BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "owner")
    private Client owner;

    @Column
    private Instant registeredDate;

    @Column
    public Instant createdAt;

    @Column
    public Instant updatedAt;

    // TODO: 부동산의 상태들을 따로 테이블로 빼는게 좋을까..? (types,,, status,,,)
    @Column
    @Enumerated
    public ContractType contractType;
    private enum ContractType { RENT, SALE, MONTH }

    @Column
    @Enumerated
    public EstateType estateType;
    private enum EstateType { APT, VILLA, LAND, STORE }

    @Column
    @Enumerated
    private EstateStatus status;
    private enum EstateStatus { ONSALE, UNDERSALE, SOLD }

    @OneToMany
    private List<Bid> biddenHistories = new ArrayList<>();

    @OneToMany
    private List<Contract> contractHistories = new ArrayList<>();
}
