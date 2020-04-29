package com.sondahum.mamas.domain.estate;

import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.domain.estate.exception.EstateAlreadyExistException;
import com.sondahum.mamas.domain.estate.model.Address;
import com.sondahum.mamas.dto.EstateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstateInfoDao {

    private final EstateRepository estateRepository;



    public Estate createEstateInfo(EstateDto.CreateReq estateDto) {
        Optional<Estate> duplicatedEstate = getDuplicatedEstate(estateDto.getName(), estateDto.getAddress());

        if (duplicatedEstate.isPresent())
            throw new EstateAlreadyExistException(duplicatedEstate.get());

        return estateRepository.save(estateDto.toEntity());
    }

    @Transactional(readOnly = true)
    public Optional<Estate> getDuplicatedEstate(String name, Address address) {
        return estateRepository.findByNameAndAddress_AndActive(name, address, true);
    }


    public Estate getEstateById(long id) {
        Optional<Estate> optionalEstate = estateRepository.findById(id);
        optionalEstate.orElseThrow(() -> new NoSuchEntityException(id));

        return optionalEstate.get();
    }

    public Estate updateEstateInfo(EstateDto.UpdateReq dto) { // User를 update할때는...?
        Optional<Estate> optionalEstate = estateRepository.findById(dto.getId());
        Estate estate = optionalEstate.orElseThrow(() -> new NoSuchEntityException(dto.getId()));

        estate.updateEstateInfo(dto); // 영속상태의 entity는 수정사항이 자동 반영된다.

        return estate;
    }

    public Estate deleteEstateInfo(Long id) {
        Optional<Estate> optionalEstate = estateRepository.deleteByIdInQuery(id);

        return optionalEstate.orElseThrow(() -> new NoSuchEntityException(id));
    }
}
