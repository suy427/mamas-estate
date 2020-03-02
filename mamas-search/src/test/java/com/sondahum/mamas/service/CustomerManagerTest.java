package com.sondahum.mamas.service;


import com.sondahum.mamas.domain.bid.BidRepository;
import com.sondahum.mamas.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerManagerTest {

    @Autowired
    TestEntityManager testEntityManager; // datajpatest annotation을 쓰면 자동으로 얘를 주입해줌.

    @Autowired
    UserRepository userRepository;

    @Autowired
    BidRepository bidRepository;

    @Test
    void createUser() {

    }


    @Test
    void getUserList() {

    }

    @Test
    void createNewUser() {
    }

    @Test
    void createNewBid() {
    }
}