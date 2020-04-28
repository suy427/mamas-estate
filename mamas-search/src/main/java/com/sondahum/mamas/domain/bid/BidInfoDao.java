package com.sondahum.mamas.domain.bid;

import com.sondahum.mamas.common.error.exception.EntityAlreadyExistException;
import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.domain.bid.exception.BidAlreadyExistException;
import com.sondahum.mamas.dto.BidDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class BidInfoDao {

    private final BidRepository bidRepository;

    // todo 호가는 똑같은걸 여러번 할 수도 있는거 아닐까..?
    public Bid createBid(BidDto.CreateReq bidDto) {
//        Optional<Bid> duplicatedBid = duplicateCheck(bidDto.toEntity());
//
//        if (duplicatedBid.isPresent())
//            throw new BidAlreadyExistException(duplicatedBid.get());

        return bidRepository.save(bidDto.toEntity());
    }

    public Bid getBidById(long id) {
        Optional<Bid> optionalBid = bidRepository.findById(id);
        optionalBid.orElseThrow(() -> new NoSuchEntityException(id));

        return optionalBid.get();
    }

//    @Transactional(readOnly = true)
//    public Optional<Bid> duplicateCheck(Bid bid) {
//        Optional<Bid> optionalBid = bidRepository.findByUser_NameAndEstate_NameAndAction_AndValidity(
//                bid.getUser().getName(),bid.getEstate().getName(), bid.getAction(), true);
//
//        return optionalBid;
//    }

    public Bid updateBidInfo(BidDto.UpdateReq dto) {
        // 여기서 찾아올 때 이미 user, estate는 수정이 되어있다.
        // 왜?? 수정 다 하고 저장할 때 이 메소드가 호출이 되니깐.
        Optional<Bid> optionalContract = bidRepository.findById(dto.getId());
        Bid bid = optionalContract.orElseThrow(() -> new NoSuchEntityException(dto.getId()));

        /* todo
         update와 contract는 update할 때, 자신의 정보 뿐 아니라 user, estate 등의 다른
         entity의 정보 변경도 일어날 수 있다. updateBidInfo()는 변경할 정보를 다 입력받고
         확정될 때 호출되는 메소드인데, 여기서는 bid고유의 정보만 update된다.
         나머지 user, estate는 따로 수정되어야한다.
        * */

        bid.updateBidInfo(dto);

        return bid;
    }

    public Bid deleteBidInfo(Long id) {
        Optional<Bid> optional = bidRepository.findById(id);
        Bid bid = optional.orElseThrow(() -> new NoSuchEntityException(id)); // todo exception 정리

        bid.setValidity(false);

        return bid;
    }

}
