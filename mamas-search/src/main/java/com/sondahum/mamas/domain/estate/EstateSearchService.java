package com.sondahum.mamas.domain.estate;


import com.sondahum.mamas.dto.EstateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.sondahum.mamas.domain.estate.QEstate.estate;


@Service
public class EstateSearchService extends QuerydslRepositorySupport {

    public EstateSearchService() {
        super(Estate.class);
    }

    public Page<Estate> search(final EstateDto.SearchReq query, final Pageable pageable) {
        List<Estate> estates = from(estate).where().fetch();


        return new PageImpl<>(estates, pageable, estates.size());
    }
}
