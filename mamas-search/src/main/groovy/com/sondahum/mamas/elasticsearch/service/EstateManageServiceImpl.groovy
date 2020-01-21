package com.sondahum.mamas.elasticsearch.service

import com.sondahum.mamas.elasticsearch.dto.EstateDto
import com.sondahum.mamas.elasticsearch.model.SearchOption
import com.sondahum.mamas.elasticsearch.repository.EstateDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EstateManageServiceImpl implements EstateManageService {

    @Autowired
    EstateDao estateDao

    @Override
    List<EstateDto> getEstateInformation() {
        return null
    }

    @Override
    List<EstateDto> searchEstateData(SearchOption searchOption) {
        return null
    }

    @Override
    List<EstateDto> updateEstateDate(List<EstateDto> estateList) {
        return null
    }

    @Override
    List<EstateDto> deleteEstateData(List<EstateDto> estateList) {
        return null
    }

    @Override
    void createEstateData(List<EstateDto> estateList) {

    }
}
