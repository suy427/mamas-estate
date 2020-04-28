package com.sondahum.mamas.jpa;


import com.sondahum.mamas.domain.bid.BidRepository;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.estate.EstateRepository;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.domain.user.UserRepository;
import com.sondahum.mamas.testutil.TestValueGenerator;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;


@DataJpaTest
public class EstateJpaTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BidRepository bidRepository;
    @Autowired
    EstateRepository estateRepository;


    /**
     *  [CONDITION]
     *      사람 생성 --> 이 사람이 주인인 땅 생성 --> 땅만 저장.
     *
     *  [EXPECTATION]
     *      1. 사람도 같이 저장될까 ?
     *      2. 저장이 된다면 그 사람의 estateList에 저장한 땅이 나올까 ?
     */
    @Test
    void 매물정보_생성() {
        User user1 = TestValueGenerator.userInfoGenerator(); // 사람 한명 생성
        Estate estate1 = TestValueGenerator.estateInfoGenerator(user1); // 이 사람의 땅 생성

        Estate resultEstate = estateRepository.save(estate1); // 땅만 저장함. owner에 user1이 들어감.
        Optional<User> optionalUser = userRepository.findByName_AndValidity(user1.getName(), true); // 이 때, user1이 찾아질까? --> 영속성 전이로 인해서..??
        User resultUser = null;

        if (optionalUser.isPresent())
            resultUser = optionalUser.get();

        MatcherAssert.assertThat(resultUser, CoreMatchers.is(user1));
        MatcherAssert.assertThat(resultEstate, CoreMatchers.is(estate1));
        MatcherAssert.assertThat(resultEstate.getOwner(), CoreMatchers.is(resultUser));

        assert resultUser != null;
        // todo 이부분까지 영속성 전이가 되어야한다.
        MatcherAssert.assertThat(resultUser.getEstateList().size(), CoreMatchers.is(0));
    }
}