package com.sondahum.mamas.domain.bid;

import com.sondahum.mamas.common.model.BaseEntity;
import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.domain.bid.model.BidStatus;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.estate.model.EstateStatus;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.dto.BidDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bid")
public class Bid extends BaseEntity implements Comparable<Bid> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "action", nullable = false)
    @Enumerated(EnumType.STRING)
    private Action action;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "estate_id", nullable = false)
    private Estate estate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "minimum", column = @Column(name = "min_price"))
            , @AttributeOverride(name = "maximum", column = @Column(name = "max_price"))})
    private Range.Price priceRange;


    private BidStatus status;

    public Bid updateBidInfo(BidDto.UpdateReq bidDto) {
        this.action = bidDto.getAction();
        this.priceRange = bidDto.getPrice();
        this.status = bidDto.getStatus();

        return this;
    }


    @Override
    public int compareTo(Bid o) {
        return this.createdDate.compareTo(o.createdDate);
    }
}
