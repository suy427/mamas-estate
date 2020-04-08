package com.sondahum.mamas.repository;


import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.estate.EstateRepository;
import com.sondahum.mamas.domain.user.User;
import com.sondahum.mamas.domain.user.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
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
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EstateRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EstateRepository estateRepository;

    @Test
    void createEstateTest() {
        List<User> initialEstateOwners = new ArrayList<>();
        List<Estate> initialEstates = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = userInfoGenerator();
            Estate estate = estateInfoGenerator(user);
            initialEstateOwners.add(user);
            initialEstates.add(estate);
        }
        estateRepository.saveAll(initialEstates);

        List<Estate> savedEstates = estateRepository.findAll();

        for (int i = 0; i < 10; i++) {
            Estate initial = initialEstates.get(i);
            Estate saved = savedEstates.get(i);

            Assert.assertThat(initial.getAddress(), CoreMatchers.is(saved.getAddress()));
            Assert.assertThat(initial.getArea(), CoreMatchers.is(saved.getArea()));
            Assert.assertThat(initial.getEstateType(), CoreMatchers.is(saved.getEstateType()));
            Assert.assertThat(initial.getContractType(), CoreMatchers.is(saved.getContractType()));
            Assert.assertThat(initial.getMarketPriceRange(), CoreMatchers.is(saved.getMarketPriceRange()));
            Assert.assertThat(initial.getOwnerRequirePriceRange(), CoreMatchers.is(saved.getOwnerRequirePriceRange()));
            Assert.assertThat(initial.getOwner(), CoreMatchers.is(saved.getOwner()));
        }
        System.out.println("break point");
    }
}