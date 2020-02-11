package com.sondahum.mamas.service;

import com.sondahum.mamas.MamasSearchTestHelper;
import com.sondahum.mamas.repository.BidRepository;
import com.sondahum.mamas.repository.UserRepository;
import com.sondahum.mamas.tester.AbstractTestHelper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class CustomerManagerTest extends MamasSearchTestHelper {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BidRepository bidRepository;

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