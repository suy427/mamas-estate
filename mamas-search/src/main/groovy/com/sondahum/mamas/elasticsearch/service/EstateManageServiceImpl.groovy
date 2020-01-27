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
        estateDao.retrieve()
    }

    @Override
    List<EstateDto> searchEstateData(SearchOption searchOption) {
        estateDao.search(searchOption)
    }

    @Override
    List<EstateDto> updateEstateDate(List<EstateDto> estateList) {
        estateDao.update(estateList)
    }

    @Override
    List<EstateDto> deleteEstateData(List<EstateDto> estateList) {
        estateDao.delete(estateList)
    }

    @Override
    void createEstateData(List<EstateDto> estateList) {
        estateDao.save(estateList)
    }
}
