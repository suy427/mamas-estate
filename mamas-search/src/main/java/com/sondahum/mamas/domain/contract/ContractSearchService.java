package com.sondahum.mamas.domain.contract;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import java.util.List;


import static com.sondahum.mamas.domain.contract.QContract.contract;



@Service
public class ContractSearchService extends QuerydslRepositorySupport {

    public ContractSearchService() {
        super(Contract.class);
    }

    public Page<Contract> search(final ContractDto.SearchReq query, final Pageable pageable) {
        List<Contract> contracts = from(contract).where().fetch();


        return new PageImpl<>(contracts, pageable, contracts.size());
    }
}
