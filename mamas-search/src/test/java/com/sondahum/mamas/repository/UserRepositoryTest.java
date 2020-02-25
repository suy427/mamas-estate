package com.sondahum.mamas.repository;

import com.sondahum.mamas.AbstractMamasSearchTest;
import com.sondahum.mamas.bid.domain.Bid;
import com.sondahum.mamas.bid.dao.BidRepository;
import com.sondahum.mamas.estate.domain.Estate;
import com.sondahum.mamas.estate.dao.EstateRepository;
import com.sondahum.mamas.user.domain.User;
import com.sondahum.mamas.user.dao.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@DataJpaTest
@RunWith(SpringRunner.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest extends AbstractMamasSearchTest{

    @Autowired UserRepository userRepository;
    @Autowired BidRepository bidRepository;
    @Autowired EstateRepository estateRepository;
    @Autowired TestEntityManager entityManager;

    @Test
    void createUserTest() { // 잘 들어가는지 테스트
        List<User> actualUsers = userInfoGenerator(10);
        userRepository.saveAll(actualUsers);

        List<User> savedUsers = userRepository.findAll();

        for (int i = 0; i < 10; i++) {
            User actual = actualUsers.get(i);
            User saved = savedUsers.get(i);

            //entity가 영속화되면서 0부터 자동으로 id가 생성되어 들어가지더라..
            //근데 또 갑자기 똑같이나오네..?ㅜㅠㅠ 뭐지...
            Assert.assertThat(actual.getId(), CoreMatchers.is(saved.getId()));
            Assert.assertThat(actual.getName(), CoreMatchers.is(saved.getName()));
            Assert.assertThat(actual.getPhone(), CoreMatchers.is(saved.getPhone()));
            Assert.assertThat(actual.getRole(), CoreMatchers.is(saved.getRole()));
        }
        System.out.println("");
    }

    /**
     * scenario
     * create user info --> create bid info --> check that bid info affects user info
     *
     * 1. create bid info of exist user --> check the user's bid list (default was null)
     *
     *   [CURRENT SCENARIO]
     *   5명의 유저가 있고, 그 중 3명의 bid정보를 생성함.
     *   1) bid정보 생성 --> estate정보 생성됨. --> estate owner정보(user정보 생성됨.)
     *   2) bid의 action이 'sell' 일 경우 owner가 bid의 주인인 user여야함. // 그나마 간단
     *   3) bid의 action이 'buy'이고
     */
    @Test
    void bidInfoCascadeTest() throws Exception {
        List<User> initialCreatedUsers = userInfoGenerator(5);
        userRepository.saveAll(initialCreatedUsers);

        List<Bid> initialCreatedBids = addBidForExistUser(0,3, initialCreatedUsers);
        bidRepository.saveAll(initialCreatedBids);

        // bid정보를 생성하면서 estate정보가 생김 --> estate가 생기면서 user가 생김.
        List<Estate> createdByBidEstates = estateRepository.findAll();

        List<User> savedUsers = userRepository.findAll();
        List<Bid> savedBids = bidRepository.findAll();

        System.out.println("");
    }

    @Test
    void deleteUserTest() {

    }

    @Test
    void updateUserTest() {

    }
}