package com.sondahum.mamas.estate;


import com.sondahum.mamas.dto.EstateDto;
import com.sondahum.mamas.estate.adaptor.out.persistence.Estate;
import com.sondahum.mamas.estate.adaptor.out.persistence.EstateRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EstateSearchService {
    private final EstateRepository estateRepository;

    public EstateSearchService(EstateRepository estateRepository) {
        this.estateRepository = estateRepository;
    }

    public List<Estate> specify(String query) {
        return null;
    }

    public Page<Estate> search(final EstateDto.SearchReq query) {
        return null;
    }
}
