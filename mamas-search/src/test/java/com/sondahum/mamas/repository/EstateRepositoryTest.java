package com.sondahum.mamas.repository;

import com.sondahum.mamas.AbstractMamasSearchTest;
import com.sondahum.mamas.estate.domain.Estate;
import com.sondahum.mamas.estate.dao.EstateRepository;
import com.sondahum.mamas.user.dao.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EstateRepositoryTest extends AbstractMamasSearchTest {

    @Autowired UserRepository userRepository;
    @Autowired EstateRepository estateRepository;

    @Test
    void createEstateTest() {
        List<Estate> actualEstates = estateInfoGenerator(10);
        estateRepository.saveAll(actualEstates);

        List<Estate> savedEstates = estateRepository.findAll();

        for (int i = 0; i < 10; i++) {
            Estate actual = actualEstates.get(i);
            Estate saved = savedEstates.get(i);

            Assert.assertThat(actual.getAddress(), CoreMatchers.is(saved.getAddress()));
            Assert.assertThat(actual.getArea(), CoreMatchers.is(saved.getArea()));
            Assert.assertThat(actual.getEstateType(), CoreMatchers.is(saved.getEstateType()));
            Assert.assertThat(actual.getContractType(), CoreMatchers.is(saved.getContractType()));
            Assert.assertThat(actual.getMarketPriceRange(), CoreMatchers.is(saved.getMarketPriceRange()));
            Assert.assertThat(actual.getOwnerRequirePriceRange(), CoreMatchers.is(saved.getOwnerRequirePriceRange()));
            Assert.assertThat(actual.getOwner(), CoreMatchers.is(saved.getOwner()));
        }
        System.out.println("");
    }
}