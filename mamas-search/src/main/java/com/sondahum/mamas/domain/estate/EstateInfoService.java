package com.sondahum.mamas.domain.estate;

import com.sondahum.mamas.common.error.exception.EntityAlreadyExistException;
import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.domain.bid.BidInfoService;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.dto.EstateDto;
import com.sondahum.mamas.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;


@Service
public class EstateInfoService {

    private static final Logger logger =  LoggerFactory.getLogger(EstateInfoService.class);
    private final EstateRepository estateRepository;

    @PersistenceContext
    private final EntityManager em;


    public EstateInfoService(EstateRepository estateRepository, EntityManager em) {
        this.estateRepository = estateRepository;
        this.em = em;
    }

    public EstateDto.DetailResponse createEstateInfo(EstateDto.CreateReq estateDto) {
        if (isSameEstateExist(estateDto))
            throw new EntityAlreadyExistException(estateDto.getName());

        Estate estate = estateRepository.save(estateDto.toEntity());

        return new EstateDto.DetailResponse(estate);
    }

    @Transactional(readOnly = true)
    boolean isSameEstateExist(EstateDto.CreateReq estateDto) {
        Optional<Estate> optionalEstate = estateRepository.findByName(estateDto.getName());

        if (optionalEstate.isPresent()) {
            Estate estate = optionalEstate.get();
            return estateDto.getAddress().equals(estate.getAddress());
        }
        return false;
    }


    public EstateDto.DetailResponse getEstateById(long id) {
        Optional<Estate> optionalEstate = estateRepository.findById(id);
        optionalEstate.orElseThrow(() -> new NoSuchEntityException(id));

        return new EstateDto.DetailResponse(optionalEstate.get());
    }

    public EstateDto.DetailResponse updateEstateInfo(long id, EstateDto.UpdateReq estateDto) { // User를 update할때는...?
        /*
            수정 페이지를 생각해보면
            -------+--+---+---+---+~~~
              소유자 | 땅 정보들 ~~~~~~~
            -------+--+---+---+---+~~~
todo                          취소, 확인

            여기서 나머지 정보들은 editText 로 두고 소유자는 링크로 하면 소유자 정보는 OwnerService에서 처리할 수 있다.
            그런데 그 후에 확인을 누르면 위의 Estate Entity는 가지고 있는 User Entity의 변경사항이 반영된채로 update가 잘 될까...?
         */
        Optional<Estate> optionalEstate = estateRepository.findById(id);
        Estate estate = optionalEstate.orElseThrow(() -> new NoSuchEntityException(id));

        // estate 엔티티를 update하기전에  EntityContext를 flush라도 한번 해줘야하는거 아닌가...?
        em.flush(); // 그래서 여기에 flush를 넣어줬다... 개 억지 같은데... 어떻게 고치지..
        estate.updateEstateInfo(estateDto);

//      userRepository.save(user) // TODO | save 안하는 이유 알아내기~~ --> EntityManager는 Entity의 변경사항을 자동으로 감시하여 반영한다.

        return new EstateDto.DetailResponse(estate);
    }

    public EstateDto.DetailResponse deleteEstateInfo(Long id) {
        Optional<Estate> optional = estateRepository.findById(id);
        Estate estate = optional.orElseThrow(() -> new NoSuchEntityException(id));

        estateRepository.deleteById(id);

        return new EstateDto.DetailResponse(estate);
    }
}
