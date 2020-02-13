package com.sondahum.mamas.repository

import com.sondahum.mamas.AbstractMamasSearchTest
import com.sondahum.mamas.domain.Bid
import com.sondahum.mamas.domain.User
import com.sondahum.mamas.model.Role
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest extends AbstractMamasSearchTest{

    @Autowired
    UserRepository userRepository;

    @Autowired
    BidRepository bidRepository

    @Test
    void createUserTest() { // 잘 들어가는지 테스트
        List<User> actualUsers = userInfoGenerator(10)
        userRepository.saveAll(actualUsers)

        List<User> savedUsers = userRepository.findAll()

        for (int i = 0; i < 10; i++) {
            User actual = actualUsers[i]
            User saved = savedUsers[i]
//            Assert.assertThat(actual.getId(), CoreMatchers.is(saved.getId())) entity가 영속화되면서 0부터 자동으로 id가 생성되어 들어가지더라..
            Assert.assertThat(actual.getName(), CoreMatchers.is(saved.getName()))
            Assert.assertThat(actual.getPhone(), CoreMatchers.is(saved.getPhone()))
            Assert.assertThat(actual.getRole(), CoreMatchers.is(saved.getRole()))
        }
    }

    /**
     * scenario
     * create user info --> create bid info --> check that bid info affects user info
     *
     * 1. create bid info of exist user --> check the user's bid list (default was null)
     * 2. create bid info of new user --> check a new user has been created
     */
    @Test
    void bidInfoCascadeTest() {
        List<User> actualUsers = userInfoGenerator(10)
        userRepository.saveAll(actualUsers)

        List<Bid> actualBidInfo = bidInfoGenerator(10, actualUsers)
        bidRepository.saveAll(actualBidInfo)

        List<User> savedUsers = userRepository.findAll()
        List<Bid> savedBids = bidRepository.findAll()

        for (int i = 0; i < 10; i++) {
            User actualUser = actualUsers[i]
            User savedUser = savedUsers[i]
            Bid savedBid = savedBids[i]
//            bidRepository
//
//            Assert.assertThat(savedUser)
        }
    }

    @Test
    void deleteUserTest() {

    }

    @Test
    void updateUserTest() {

    }
}