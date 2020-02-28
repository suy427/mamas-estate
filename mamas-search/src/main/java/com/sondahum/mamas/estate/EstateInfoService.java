package com.sondahum.mamas.estate;

import com.sondahum.mamas.common.error.exception.EntityAlreadyExistException;
import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.estate.dao.EstateRepository;
import com.sondahum.mamas.estate.domain.Estate;
import com.sondahum.mamas.estate.dto.EstateDto;
import com.sondahum.mamas.user.domain.User;
import com.sondahum.mamas.user.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            throw new EntityAlreadyExistException(estateDto.getName());

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

    @Transactional(readOnly = true)
    public Page<Estate> searchEstates(final EstateDto.SearchReq query, final Pageable pageable) {
        Page<Estate> searchResult;

        if (query == null) {
            searchResult = estateRepository.findAll(pageable);
        } else {
            searchResult = null;
        }

        return searchResult;
    }

    public EstateDto.DetailResponse deleteEstateInfo(Long id) {
        Optional<Estate> optional = estateRepository.findById(id);
        Estate estate = optional.orElseThrow(() -> new NoSuchEntityException(id));

        estateRepository.deleteById(id);

        return new EstateDto.DetailResponse(estate);
    }
}