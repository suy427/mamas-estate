package com.sondahum.mamas.domain.estate;

import com.sondahum.mamas.common.model.BaseEntity;
import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.domain.estate.model.Address;
import com.sondahum.mamas.domain.estate.model.ContractType;
import com.sondahum.mamas.domain.estate.model.EstateType;
import com.sondahum.mamas.domain.estate.model.Status;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.dto.EstateDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@Entity
@Table(name = "estate")
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "estate_id"))
public class Estate extends BaseEntity implements Serializable {


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
    private Status status;

    @OneToMany
    @JoinColumn(name = "bid_id")
    private List<Bid> bidList;

    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime registeredDate;


    public void updateEstateInfo(EstateDto.UpdateReq dto) {
        this.name = dto.getName();
        this.address = dto.getAddress();
        this.area = dto.getArea();
        this.status = dto.getStatus();
        this.contractType = dto.getContractType();
        this.estateType = dto.getEstateType();
        this.ownerRequirePriceRange = dto.getOwnerRequirePriceRange();
        this.marketPriceRange = dto.getMarketPriceRange();
    }
}

