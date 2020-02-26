package com.sondahum.mamas.estate.service;

import com.sondahum.mamas.estate.dao.EstateRepository;
import com.sondahum.mamas.estate.domain.Estate;
import com.sondahum.mamas.estate.dto.EstateDto;
import com.sondahum.mamas.estate.exception.EstateAlreadyExistException;
import com.sondahum.mamas.user.exception.UserAlreadyExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class EstateInfoService {

    private final EstateRepository estateRepository;



    public EstateInfoService(EstateRepository estateRepository) {
        this.estateRepository = estateRepository;
    }

    public EstateDto.DetailResponse createEstateInfo(EstateDto.CreateReq estateDto) {
        if (isSameEstateExist(estateDto))
            throw new EstateAlreadyExistException(estateDto);

        Estate estate = estateRepository.save(estateDto.toEntity());

        return new EstateDto.DetailResponse(estate);
    }

    @Transactional(readOnly = true)
    public boolean isSameEstateExist(EstateDto.CreateReq estateDto) {
        Optional<Estate> optionalEstate = estateRepository.findByName(estateDto.getName());

        if (optionalEstate.isPresent()) {
            Estate estate = optionalEstate.get();
            return estateDto.getAddress().equals(estate.getAddress());
        }
        return false;
    }


    public EstateDto.DetailResponse getEstateById(long id) {
        return null;
    }

    public EstateDto.DetailResponse updateEstateInfo(long id, EstateDto.UpdateReq dto) {
        return null;
    }
}
