package com.sondahum.mamas.repository;

import com.sondahum.mamas.MamasSearchTestHelper;
import com.sondahum.mamas.domain.User;
import com.sondahum.mamas.dto.UserDto;
import com.sondahum.mamas.model.Role;
import com.sondahum.mamas.tester.MamasTestStarter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @DataJpaTest 어노테이션은 기본적으로 jpa관련된 bean만 가져와서 테스트 하므로 전체 테스트보다 빠르다.
 * 메모리기반 가상 데이터베이스를 사용하지만 @AutoConfigureTestDatabase(replace = Replace.NONE) 옵션으로 실 DB사용도 가능하다.
 */
@DataJpaTest
//@AutoConfigureTestDatabase/*(replace = AutoConfigureTestDatabase.Replace.NONE)*/
class UserRepositoryTest extends MamasSearchTestHelper {

    @Autowired
    TestEntityManager testEntityManager; // datajpatest annotation을 쓰면 자동으로 얘를 주입해줌.

    @Autowired
    UserRepository ur;

    @Test
    void createUser() {
        UserDto userDto = new UserDto();
        userDto.setName("james");
        userDto.setPhone("010-3002-9543");
        userDto.setRole(Role.OTHER);

        User userEntity = userDto.toEntity();

        ur.save(userEntity);
    }

    @Test
    void createBid() {

    }

    @Test
    void getUserList() {

    }

}