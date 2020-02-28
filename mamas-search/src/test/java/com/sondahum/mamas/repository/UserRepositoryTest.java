package com.sondahum.mamas.repository;

import com.sondahum.mamas.AbstractMamasSearchTest;
import com.sondahum.mamas.domain.user.User;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
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
        List<User> actualUsers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            actualUsers.add(userInfoGenerator());
        }

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

    @Test
    void deleteUserTest() {

    }

    @Test
    void updateUserTest() {

    }
}