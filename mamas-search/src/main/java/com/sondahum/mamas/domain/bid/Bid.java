package com.sondahum.mamas.domain.bid;

import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.dto.BidDto;
import com.sondahum.mamas.dto.ContractDto;
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
@NoArgsConstructor
@AllArgsConstructor
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
    private Range.Price priceRange;

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    @CreatedDate
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    @LastModifiedDate
    private LocalDate modifiedDate;


    public void updateBidInfo(BidDto.UpdateReq bidDto) {
        this.user.setName(bidDto.getUser());      // todo 이런식으로 여기서 user나 estate를 수정해도 되는건가...
        this.estate.setName(bidDto.getEstate());
        this.action = Action.findByName(bidDto.getAction());
        this.priceRange = bidDto.getPrice();
    }


    @Override
    public int compareTo(Bid o) {
        return this.createdDate.compareTo(o.createdDate);
    }
}
