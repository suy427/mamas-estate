package com.sondahum.mamas.bid.domain;

import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.contract.domain.Contract;
import com.sondahum.mamas.estate.domain.Estate;
import com.sondahum.mamas.user.domain.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "bid")
public class Bid implements Serializable, Comparable<Bid> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "action", nullable = false)
    @Enumerated(EnumType.STRING)
    private Action action;

    @OneToOne
    @JoinColumn(name = "estate_id", nullable = false)
    private Estate estate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "minimum", column = @Column(name = "min_price"))
            , @AttributeOverride(name = "maximum", column = @Column(name = "max_price"))})
    private Range.PriceRange priceRange;

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    @CreatedDate
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    @LastModifiedDate
    private LocalDate modifiedDate;


    @Override
    public int compareTo(Bid o) {
        return this.createdDate.compareTo(o.createdDate);
    }
}
