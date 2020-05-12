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


@Slf4j
@DataJpaTest
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
     */
    @Test
    void 호가정보_생성() {
        User user1 = TestValueGenerator.userInfoGenerator();
        Estate estate1 = TestValueGenerator.estateInfoGenerator(user1);

        Bid bid = TestValueGenerator.bidInfoGenerator(user1, estate1);
        bid.setAction(Action.SELL);


        Bid savedBid = bidRepository.save(bid);

        // persisted by cascade by bid
        Optional<User> optionalUser = userRepository.findByName_AndActiveTrue(user1.getName());
        Optional<Estate> optionalEstate = estateRepository.findByName_AndActiveTrue(estate1.getName());

        User savedUser = null;
        Estate savedEstate = null;

        if (optionalUser.isPresent())
            savedUser = optionalUser.get();

        if (optionalEstate.isPresent())
            savedEstate = optionalEstate.get();

        MatcherAssert.assertThat(savedBid, CoreMatchers.is(bid));
        MatcherAssert.assertThat(savedEstate, CoreMatchers.is(estate1));
        MatcherAssert.assertThat(savedUser, CoreMatchers.is(user1));

        MatcherAssert.assertThat(savedBid.getUser(), CoreMatchers.is(savedUser));
        MatcherAssert.assertThat(savedBid.getEstate(), CoreMatchers.is(savedEstate));

        MatcherAssert.assertThat(savedEstate.getOwner(), CoreMatchers.is(savedUser));
    }
}
