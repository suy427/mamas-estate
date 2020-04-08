package com.sondahum.mamas.repository;

import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.bid.BidRepository;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.estate.EstateRepository;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.domain.user.UserRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.sondahum.mamas.testutil.TestValueGenerator.*;

import java.util.ArrayList;
import java.util.List;


@DataJpaTest
@RunWith(SpringRunner.class)
public class BidRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BidRepository bidRepository;
    @Autowired
    EstateRepository estateRepository;

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

    @Ignore(value = "not now")
    @Test
    void bidInfoCascadeTest() {
        List<User> initialCreatedOwnerGroup = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            initialCreatedOwnerGroup.add(userInfoGenerator());
        }
        List<User> savedOwnerGroup = userRepository.saveAll(initialCreatedOwnerGroup);


        List<User> initialCreatedNonOwnerGroup = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            initialCreatedNonOwnerGroup.add(userInfoGenerator());
        }
        List<User> savedNonOwnerGroup = userRepository.saveAll(initialCreatedNonOwnerGroup);

//      #################################################################################
//      ##
//      ## ownerGroup의 5명은 아래에서 만들어지는 땅을 갖는다.
//      ##
//      #################################################################################
        List<Estate> initialCreatedEstates = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            initialCreatedEstates.add(estateInfoGenerator(initialCreatedNonOwnerGroup.get(i)));
        }
        List<Estate> savedEstates = estateRepository.saveAll(initialCreatedEstates);


//      #################################################################################
//      ##
//      ## nonOwnerGroup이 bidding을 한다.
//      ##
//      #################################################################################
        List<Bid> initialCreatedBids = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            initialCreatedBids.add(bidInfoGenerator(savedNonOwnerGroup.get(i), Action.BUY, savedEstates.get(i)));
        }
        List<Bid> savedBids = bidRepository.saveAll(initialCreatedBids);

        List<User> finalUsers = userRepository.findAll();
        List<Estate> finalEstates = estateRepository.findAll();
        List<Bid> finalBids = bidRepository.findAll();


        System.out.println("break point");
    }

}