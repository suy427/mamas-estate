package com.sondahum.mamas.contract.exception;


import com.sondahum.mamas.contract.adaptor.out.persistence.Contract;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ContractAlreadyExistException extends RuntimeException {

    private String message;
    private final Contract contract;

    public ContractAlreadyExistException(Contract contract) {
        this.contract = contract;
    }

}
