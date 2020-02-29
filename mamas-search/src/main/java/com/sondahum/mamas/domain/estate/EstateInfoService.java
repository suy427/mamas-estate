package com.sondahum.mamas.domain.estate;

import com.sondahum.mamas.common.error.exception.EntityAlreadyExistException;
import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.dto.EstateDto;
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

    public EstateDto.DetailResponse updateEstateInfo(long id, EstateDto.UpdateReq estateDto) {
        Optional<Estate> optional = estateRepository.findById(id);
        Estate estate = optional.orElseThrow(() -> new NoSuchEntityException(id));

        estate.updateEstateInfo(estateDto);// 예제에 보면 따로 repository에 변경된 entity를 save하지 않는다.
//        userRepository.save(user) // TODO 이유 알아내기~~

        return new EstateDto.DetailResponse(estate);
    }

    public EstateDto.DetailResponse deleteEstateInfo(Long id) {
        Optional<Estate> optional = estateRepository.findById(id);
        Estate estate = optional.orElseThrow(() -> new NoSuchEntityException(id));

        estateRepository.deleteById(id);

        return new EstateDto.DetailResponse(estate);
    }
}
