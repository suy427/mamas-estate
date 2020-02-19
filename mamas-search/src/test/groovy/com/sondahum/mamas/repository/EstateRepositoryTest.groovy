package com.sondahum.mamas.repository

import com.sondahum.mamas.AbstractMamasSearchTest
import com.sondahum.mamas.estate.domain.Estate
import com.sondahum.mamas.estate.repository.EstateRepository
import com.sondahum.mamas.user.repository.UserRepository
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
class EstateRepositoryTest extends AbstractMamasSearchTest {

    @Autowired UserRepository userRepository
    @Autowired EstateRepository estateRepository

    @Test
    void createEstateTest() {
        List<Estate> actualEstates = estateInfoGenerator(10)
        estateRepository.saveAll(actualEstates)

        List<Estate> savedEstates = estateRepository.findAll()

        for (int i = 0; i < 10; i++) {
            Estate actual = actualEstates[i]
            Estate saved = savedEstates[i]

            Assert.assertThat(actual.getAddress1(), CoreMatchers.is(saved.getAddress1()))
            Assert.assertThat(actual.getAddress2(), CoreMatchers.is(saved.getAddress2()))
            Assert.assertThat(actual.getAddress3(), CoreMatchers.is(saved.getAddress3()))
            Assert.assertThat(actual.getArea(), CoreMatchers.is(saved.getArea()))
            Assert.assertThat(actual.getEstateType(), CoreMatchers.is(saved.getEstateType()))
            Assert.assertThat(actual.getContractType(), CoreMatchers.is(saved.getContractType()))
            Assert.assertThat(actual.getMarketMinimumPrice(), CoreMatchers.is(saved.getMarketMinimumPrice()))
            Assert.assertThat(actual.getOwnerMaximumPrice(), CoreMatchers.is(saved.getOwnerMaximumPrice()))
            Assert.assertThat(actual.getOwner(), CoreMatchers.is(saved.getOwner()))
        }
        println ""  // break point
    }
}