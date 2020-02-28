package com.sondahum.mamas.domain;

import com.sondahum.mamas.dto.ContractDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "contract")
public class Contract implements Serializable, Comparable<Contract> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seller", nullable = false)
    private User seller;

    @ManyToOne
    @JoinColumn(name = "buyer", nullable = false)
    private User buyer;

    @Column(name = "price", nullable = false)
    private Long price;

    @ManyToOne
    @JoinColumn(name = "estate_id", nullable = false)
    private Estate estate;

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    @CreatedDate
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
    @LastModifiedDate
    private LocalDate modifiedDate;

    public void updateContractInfo(ContractDto.UpdateReq contractDto) {
        this.seller.setName(contractDto.getSeller());
        this.buyer.setName(contractDto.getBuyer());
        this.estate.setName(contractDto.getEstate());
        this.price = contractDto.getPrice();
        this.createdDate = contractDto.getContractedDate();
    }

    @Override
    public int compareTo(Contract o) {
        return this.createdDate.compareTo(o.getCreatedDate());
    }


}
