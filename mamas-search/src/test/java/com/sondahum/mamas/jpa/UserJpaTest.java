package com.sondahum.mamas.jpa;


import com.sondahum.mamas.domain.bid.BidRepository;
import com.sondahum.mamas.domain.estate.EstateRepository;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.domain.user.UserRepository;
import com.sondahum.mamas.testutil.TestValueGenerator;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class UserJpaTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BidRepository bidRepository;
    @Autowired
    EstateRepository estateRepository;


    @Test
    void 유저_생성() {
        User user1 = TestValueGenerator.userInfoGenerator();
        User result = userRepository.save(user1);

        MatcherAssert.assertThat(result, CoreMatchers.is(user1));
    }
}