package com.sondahum.mamas.domain.contract.exception;


import com.sondahum.mamas.domain.contract.Contract;
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
