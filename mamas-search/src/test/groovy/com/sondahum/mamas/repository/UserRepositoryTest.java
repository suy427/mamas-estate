package com.sondahum.mamas.repository;

import com.sondahum.mamas.dto.UserDto;
import com.sondahum.mamas.model.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository ur;

    @Test
    void createUser() {
        UserDto user = new UserDto();
        user.setName("james");
        user.setPhone("010-3002-9543");
    }

    @Test
    void createBid() {

    }

    @Test
    void getUserList() {

    }

}