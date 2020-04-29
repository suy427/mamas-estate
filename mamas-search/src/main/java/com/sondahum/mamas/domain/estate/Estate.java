package com.sondahum.mamas.domain.estate;

import com.sondahum.mamas.common.model.BaseEntity;
import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.domain.contract.Contract;
import com.sondahum.mamas.domain.estate.model.Address;
import com.sondahum.mamas.domain.estate.model.ContractType;
import com.sondahum.mamas.domain.estate.model.EstateType;
import com.sondahum.mamas.domain.estate.model.EstateStatus;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.dto.EstateDto;
import lombok.*;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@Entity
@Table(name = "estate")
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "estate_id"))
public class Estate extends BaseEntity {


    @Column(name = "estate_name")
    private String name;

    @Embedded
    private Address address;

    @Column(name = "area")
    private Double area;

    @Enumerated(EnumType.STRING)
    @Column(name = "contract_type")
    private ContractType contractType;

    @Enumerated(EnumType.STRING)
    @Column(name = "estate_type")
    private EstateType estateType;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "minimum", column = @Column(name = "market_min_price"))
            , @AttributeOverride(name = "maximum", column = @Column(name = "market_max_price"))})
    private Range.Price marketPriceRange;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "minimum", column = @Column(name = "owner_min_price"))
            , @AttributeOverride(name = "maximum", column = @Column(name = "owner_max_price"))})
    private Range.Price ownerRequirePriceRange;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EstateStatus status;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bid_id")
    private List<Bid> bidList;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "owner")
    private User owner;

    @Column(name = "registered_date")
    private LocalDateTime registeredDate;

    @Builder.Default // todo 이거 뭔지 다시 확인
    @OneToMany(cascade = CascadeType.PERSIST) //todo 연관관계 이렇게 안하면 에러남.. 확인
    private List<Contract> contractHistoryList = new ArrayList<>();

    public void addContractHistory(Contract contract) {
        contractHistoryList.add(contract);
    }

    public void addBidHistory(Bid bid) {
        bidList.add(bid);
    }

    public Estate updateEstateInfo(EstateDto.UpdateReq dto) {
        this.name = dto.getName();
        this.address = dto.getAddress();
        this.area = dto.getArea();
        this.status = dto.getStatus();
        this.contractType = dto.getContractType();
        this.estateType = dto.getEstateType();
        this.ownerRequirePriceRange = dto.getOwnerRequirePriceRange();
        this.marketPriceRange = dto.getMarketPriceRange();

        return this;
    }
}

