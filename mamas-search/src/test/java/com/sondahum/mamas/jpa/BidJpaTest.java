package com.sondahum.mamas.jpa;

import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.bid.BidRepository;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.estate.EstateRepository;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.domain.user.UserRepository;
import com.sondahum.mamas.testutil.TestValueGenerator;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@DataJpaTest
@Slf4j
public class BidJpaTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BidRepository bidRepository;
    @Autowired
    EstateRepository estateRepository;


    /**
     *  [CONDITION]
     *      사람 생성 --> 이 사람이 주인인 땅 생성 --> 이 땅의 판매 호가 생성 --> 호가 정보만 저장.
     *
     *  [EXPECTATION]
     *      1. 사람도 같이 저장될까 ? -> O
     *      2. 땅도 같이 저장될까 ? -> O
     *      3. 저장이 된다면 그 사람의 estateList에 저장한 땅이 나올까 ?
     *      4. 저장이 된다면 그 사람의 bidList에 저장한 bid가 나올까 ?
     *      5. 저장이 된다면 그 땅의 bidList에 저장한 bid가 나올까 ?
     */
    @Test
    void 호가정보_생성() {
        User user1 = TestValueGenerator.userInfoGenerator();
        Estate estate1 = TestValueGenerator.estateInfoGenerator(user1);
        Bid bid1 = TestValueGenerator.bidInfoGenerator(user1, estate1);
        bid1.setAction(Action.SELL);


        Bid savedBid = bidRepository.save(bid1);

        Optional<User> optionalUser = userRepository.findByName_AndValidity(user1.getName(), true);
        Optional<Estate> optionalEstate = estateRepository.findByName_AndValidity(estate1.getName(), true);

        User savedUser = null;
        Estate savedEstate = null;

        if (optionalUser.isPresent())
            savedUser = optionalUser.get();

        if (optionalEstate.isPresent())
            savedEstate = optionalEstate.get();

        MatcherAssert.assertThat(savedBid, CoreMatchers.is(bid1));
        MatcherAssert.assertThat(savedEstate, CoreMatchers.is(estate1));
        MatcherAssert.assertThat(savedUser, CoreMatchers.is(user1));

        MatcherAssert.assertThat(savedBid.getUser(), CoreMatchers.is(savedUser));
        MatcherAssert.assertThat(savedBid.getEstate(), CoreMatchers.is(savedEstate));

//        MatcherAssert.assertThat(savedUser.getBidList().size(), CoreMatchers.is(0));
        MatcherAssert.assertThat(savedUser.getEstateList().size(), CoreMatchers.is(0));

        MatcherAssert.assertThat(savedEstate.getOwner(), CoreMatchers.is(savedUser));
//        MatcherAssert.assertThat(savedEstate.getBidList().size(), CoreMatchers.is(0));
    }

//    @Test
//    void 유저_호가_컬렉션_추가() {
//        User user1 = TestValueGenerator.userInfoGenerator();
//        Estate estate1 = TestValueGenerator.estateInfoGenerator(user1);
//
//        User savedUser1 = userRepository.save(user1);
//        Estate savedEstate1 = estateRepository.save(estate1);
//
//        Bid bid1 = TestValueGenerator.bidInfoGenerator(savedUser1, savedEstate1);
//
//        Bid savedBid1 = bidRepository.save(bid1);
//
//        MatcherAssert.assertThat(savedUser1, CoreMatchers.is(user1));
//
//
//        user1.getBidList().add(bid1);
//
//
//    }

}