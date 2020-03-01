package com.sondahum.mamas.domain.contract;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.dto.ContractDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;


import static com.sondahum.mamas.domain.contract.QContract.contract;



@Service
public class ContractSearchService extends QuerydslRepositorySupport {

    private final ContractRepository contractRepository;

    public ContractSearchService(ContractRepository contractRepository) {
        super(Contract.class);
        this.contractRepository = contractRepository;
    }

    public Page<Contract> search(final ContractDto.SearchReq query, final Pageable pageable) {
        if (query == null) {
            return contractRepository.findAll(pageable);
        }

        List<Contract> contracts = from(contract).where(
                estate(query.getEstate())
                , buyer(query.getBuyer())
                , seller(query.getSeller())
                , contractedDate(query.getContractedDate())
                , contractedPrice(query.getContractedPrice())
        ).fetch();


        return new PageImpl<>(contracts, pageable, contracts.size());
    }

    private BooleanExpression estate(String estate) {
        if (StringUtils.isEmpty(estate)) return null;

        return contract.estate.name.likeIgnoreCase("%"+estate+"%");
    }

    private BooleanExpression buyer(String buyer) {
        if (StringUtils.isEmpty(buyer)) return null;

        return contract.buyer.name.likeIgnoreCase("%"+buyer+"%");
    }

    private BooleanExpression seller(String seller) {
        if (StringUtils.isEmpty(seller)) return null;

        return contract.seller.name.likeIgnoreCase("%"+seller+"%");
    }

    private BooleanExpression contractedPrice(Range.Price priceRange) {
        if (priceRange == null) return null;

        return contract.price.between(priceRange.getMinimum(), priceRange.getMaximum());
    }

    private BooleanExpression contractedDate(Range.Date dateRange) {
        if (dateRange == null) return null;

        return contract.createdDate.between(dateRange.getMinimum(), dateRange.getMaximum());
    }
}
