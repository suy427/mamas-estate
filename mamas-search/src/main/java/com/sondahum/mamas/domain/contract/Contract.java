package com.sondahum.mamas.domain.contract;

import com.sondahum.mamas.common.model.BaseEntity;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.estate.model.ContractType;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.dto.ContractDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contract")
@AttributeOverride(name = "id", column = @Column(name = "contract_id"))
public class Contract extends BaseEntity implements Comparable<Contract> {

    @ManyToOne//(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "seller", nullable = false)
    private User seller;

    @ManyToOne//(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "buyer", nullable = false)
    private User buyer;

    @Column(name = "price", nullable = false)
    private Long price;

    @ManyToOne//(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "estate_id", nullable = false)
    private Estate estate;

    @Column(name = "contract_type")
    private ContractType contractType;

    private LocalDateTime contractedDate;

    private LocalDateTime expireDate;


    public void updateContractInfo(ContractDto.UpdateReq contractDto) {
        this.price = contractDto.getPrice();
        this.contractedDate = contractDto.getContractedDate();
    }

    @Override
    public int compareTo(Contract o) {
        return this.contractedDate.compareTo(o.getCreatedDate());
    }

    @Override
    public String toString() {
        return estate+", 매도 : "+seller+", 매수 : "+buyer+" ("+contractedDate.toString()+")";
    }
}
