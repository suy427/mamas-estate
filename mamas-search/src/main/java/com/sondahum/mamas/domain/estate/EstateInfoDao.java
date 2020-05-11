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


    public Estate createEstateInfo(Estate estate) {
        Optional<Estate> duplicatedEstate = getDuplicatedEstate(estate.getName(), estate.getAddress());

        if (duplicatedEstate.isPresent())
            throw new EstateAlreadyExistException(duplicatedEstate.get());

        return estateRepository.save(estate);
    }

    public Estate findEstateByName(String name) {
        return estateRepository.findByName_AndActiveTrue(name)
                .orElseThrow(() -> new NoSuchEntityException(1L));
    }

    @Transactional(readOnly = true)
    public Optional<Estate> getDuplicatedEstate(String name, Address address) {
        return estateRepository.findByNameAndAddress_AndActiveTrue(name, address);
    }


    public Estate getEstateById(long id) {
        Optional<Estate> optionalEstate = estateRepository.findById(id);
        optionalEstate.orElseThrow(() -> new NoSuchEntityException(id));

        return optionalEstate.get();
    }

    public Estate updateEstateInfo(long id, EstateDto.UpdateReq dto) { // User를 update할때는...?
        Optional<Estate> optionalEstate = estateRepository.findById(id);
        Estate estate = optionalEstate.orElseThrow(() -> new NoSuchEntityException(id));

        return estate.updateEstateInfo(dto); // 영속상태의 entity는 수정사항이 자동 반영된다.
    }

    public Estate deleteEstateSoft(Long id) {
        Estate estate = estateRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(id));

        estate.setActive(false);
        return estate;
    }

    public void deleteEstateHard(long id) {
        estateRepository.deleteById(id);
    }
}
