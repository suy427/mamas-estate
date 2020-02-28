package com.sondahum.mamas.domain.estate;

import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.domain.bid.Bid;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@Entity
@Table(name = "estate")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Estate implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estate_id")
    private Long id;

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
    private Range<Long> marketPriceRange;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "minimum", column = @Column(name = "owner_min_price"))
            , @AttributeOverride(name = "maximum", column = @Column(name = "owner_max_price"))})
    private Range<Long> ownerRequirePriceRange;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany
    @JoinColumn(name = "bid_id")
    private List<Bid> bidList;

    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    @CreatedDate
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    @LastModifiedDate
    private LocalDate modifiedDate;
}
