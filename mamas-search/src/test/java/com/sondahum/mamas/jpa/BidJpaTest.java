package com.sondahum.mamas.jpa;

import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.bid.BidRepository;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.estate.EstateRepository;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.domain.user.UserRepository;
import com.sondahum.mamas.testutil.TestValueGenerator;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

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


    @Test
    void 호가정보_생성() {
        User user1 = TestValueGenerator.userInfoGenerator();
        Estate estate1 = TestValueGenerator.estateInfoGenerator(user1);

        Bid bid1 = TestValueGenerator.bidInfoGenerator(user1, estate1);
        Bid result = bidRepository.save(bid1);

        MatcherAssert.assertThat(result, CoreMatchers.is(bid1));
    }

    @Test
    void 유저_호가_컬렉션_추가() {
        User user1 = TestValueGenerator.userInfoGenerator();
        Estate estate1 = TestValueGenerator.estateInfoGenerator(user1);

        User savedUser1 = userRepository.save(user1);
        Estate savedEstate1 = estateRepository.save(estate1);

        Bid bid1 = TestValueGenerator.bidInfoGenerator(savedUser1, savedEstate1);

        Bid savedBid1 = bidRepository.save(bid1);

        MatcherAssert.assertThat(savedUser1, CoreMatchers.is(user1));


        user1.getBidList().add(bid1);


    }

}