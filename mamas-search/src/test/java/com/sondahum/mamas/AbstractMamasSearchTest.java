package com.sondahum.mamas;

import com.sondahum.mamas.common.model.*;
import com.sondahum.mamas.domain.bid.Action;
import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.estate.Address;
import com.sondahum.mamas.domain.estate.ContractType;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.estate.EstateType;
import com.sondahum.mamas.domain.user.Phone;
import com.sondahum.mamas.domain.user.Role;
import com.sondahum.mamas.domain.user.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public abstract class AbstractMamasSearchTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private Random random = new Random();

    /******************************
     *
     *      RANDOM INFO GENERATORS
     *
     *******************************/
    @Test
    public String randomStringGenerator(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    @Test
    protected Phone randomPhoneNumberGenerator() {
        String middle = RandomStringUtils.randomNumeric(4);
        String last = RandomStringUtils.randomNumeric(4);

        return Phone.builder().first("010").middle(middle).last(last).build();
    }

    @Test
    protected Address randomAddressGenerator() {
        return Address.builder()
                .address1(randomStringGenerator(5))
                .address2(randomStringGenerator(5))
                .address3(randomStringGenerator(5)).build();
    }

    @Test
    protected Long randomIdGenerator() {
        return random.nextLong();
    }

    @Test
    protected Integer randomNumbersGenerator(int length) {
        return random.nextInt(length);
    }

    @Test
    protected Range<Double> randomAreaGenerator() {
        Double min = Double.parseDouble(RandomStringUtils.randomNumeric(3));
        Double max = Double.parseDouble(RandomStringUtils.randomNumeric(3));

        while (max < min)
            max = Double.parseDouble(RandomStringUtils.randomNumeric(3));

        return Range.<Double>builder().minimum(min).maximum(max).build();
    }

    @Test
    protected Role randomRoleGenerator() {
        return Role.findByValue(random.nextInt(3) + 1);
    }

    @Test
    protected Action randomActionGenerator() {
        return Action.findByValue(random.nextInt(3) + 1);
    }

    @Test
    protected Names randomNameGenerator() {
        return Names.findByValue(random.nextInt(29)+1);
    }

    @Test
    protected ContractType randomContractTypeGenerator() {
        return ContractType.findByValue(random.nextInt(3) + 1);
    }

    @Test
    protected EstateType randomEstateTypeGenerator() {
        return EstateType.findByValue(random.nextInt(4) + 1);
    }

    @Test
    protected Range<Long> randomPriceRangeGenerator() {
        Long min = Long.parseLong(RandomStringUtils.randomNumeric(8));
        Long max = Long.parseLong(RandomStringUtils.randomNumeric(8));

        while (max < min)
            max = Long.parseLong(RandomStringUtils.randomNumeric(8));

        return Range.<Long>builder().minimum(min).maximum(max).build();
    }

    /********************************
     *
     *      USER INFO GENERATOR
     *
     ********************************/
    protected User userInfoGenerator() {
        return User.builder()
                .name(randomNameGenerator().name)
                .phone(randomPhoneNumberGenerator())
                .role(randomRoleGenerator()).build();
    }

    /********************************
     *
     *      ESTATE INFO GENERATOR
     *
     ********************************/
    @Test
    protected Estate estateInfoGenerator(User owner) { // TODO 값 채워넣기
        return Estate.builder()
                .address(randomAddressGenerator())
                .area(random.nextDouble())
                .contractType(randomContractTypeGenerator())
                .estateType(randomEstateTypeGenerator())
                .marketPriceRange(randomPriceRangeGenerator())
                .ownerRequirePriceRange(randomPriceRangeGenerator())
                .owner(owner).build();
    }


    /********************************
     *
     *      BID INFO GENERATOR
     *
     ********************************/
    @Test
    protected Bid bidInfoGenerator(User user, Action action, Estate estate) {
        return Bid.builder()
                .user(user)
                .estate(estate)
                .action(action)
                .priceRange(randomPriceRangeGenerator()).build();
    }

}
