package com.sondahum.mamas.domain.estate;

import com.sondahum.mamas.common.error.exception.EntityAlreadyExistException;
import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
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
        if (!isSameEstateExist(estateDto))
            throw new EntityAlreadyExistException(estateDto.getName());

        Estate estate = estateRepository.save(estateDto.toEntity());

        return estate;
    }

    @Transactional(readOnly = true)
    boolean isSameEstateExist(EstateDto.CreateReq estateDto) {
        Optional<Estate> optionalEstate =
                estateRepository.findByNameAndAddress_AndValidity(estateDto.getName(), estateDto.getAddress());

        return optionalEstate.isPresent();
    }


    public Estate getEstateById(long id) {
        Optional<Estate> optionalEstate = estateRepository.findById(id);
        optionalEstate.orElseThrow(() -> new NoSuchEntityException(id));

        return optionalEstate.get();
    }

    public Estate updateEstateInfo(EstateDto.UpdateReq dto) { // User를 update할때는...?
        /*
            수정 페이지를 생각해보면
             ------+--+---+---+---+~~~
            소유자 │  땅 정보들 ~~~~~~~
            -------+--+---+---+---+~~~
todo                       취소, 확인

            여기서 나머지 정보들은 editText 로 두고 소유자는 링크로 하면 소유자 정보는 OwnerService에서 처리할 수 있다.
            그런데 그 후에 확인을 누르면 위의 Estate Entity는 가지고 있는 User Entity의 변경사항이 반영된채로 update가 잘 될까...?
            ----Answer--->>
            이 고민은 바보같이 updateEstateInfo메소드가 수정페이지에 들어온 순간부터 호출이 되는걸로 착각을 해서 한것 같다.
            확인을 누르는 순간에 메소드가 호출이 되기때문에, 이미 수정된 채로 이 메소드가 호출이되고,
            수정된 정보로 estate entity를 가져온다.
         */
        Optional<Estate> optionalEstate = estateRepository.findById(dto.getId());
        Estate estate = optionalEstate.orElseThrow(() -> new NoSuchEntityException(dto.getId()));

        estate.updateEstateInfo(dto);

//      userRepository.save(user) // TODO | save 안하는 이유 알아내기~~ --> EntityManager는 Entity의 변경사항을 자동으로 감시하여 반영한다.

        return estate;
    }

    public Estate deleteEstateInfo(Long id) {
        Optional<Estate> optional = estateRepository.findById(id);
        Estate estate = optional.orElseThrow(() -> new NoSuchEntityException(id));

        estate.setValidity(false);

        return estate;
    }
}
