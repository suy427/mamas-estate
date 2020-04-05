package com.sondahum.mamas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sondahum.mamas.common.model.Range;
import com.sondahum.mamas.domain.bid.model.Action;
import com.sondahum.mamas.domain.bid.Bid;
import com.sondahum.mamas.domain.estate.model.Address;
import com.sondahum.mamas.domain.estate.model.ContractType;
import com.sondahum.mamas.domain.estate.Estate;
import com.sondahum.mamas.domain.estate.model.EstateType;
import com.sondahum.mamas.domain.user.model.Role;
import com.sondahum.mamas.domain.user.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Random;


//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = MamasEstateApplicationStarter.class)
@Ignore
public class TestValueGenerator {

    private static Random random = new Random();
    protected MockMvc mockMvc;
    protected final ObjectMapper mapper = new ObjectMapper();

    /******************************
     *
     *      RANDOM INFO GENERATORS
     *
     *******************************/

    public static String randomStringGenerator(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }


    public static String randomPhoneNumberGenerator() {
        String middle = RandomStringUtils.randomNumeric(4);
        String last = RandomStringUtils.randomNumeric(4);

        return "010"+middle+last;
    }


    public static Address randomAddressGenerator() {
        return Address.builder()
                .address1(randomStringGenerator(5))
                .address2(randomStringGenerator(5))
                .address3(randomStringGenerator(5)).build();
    }


    public static Long randomIdGenerator() {
        return random.nextLong();
    }


    public static Integer randomNumbersGenerator(int length) {
        return random.nextInt(length);
    }


    public static Range.Area randomAreaGenerator() {
        double min = Double.parseDouble(RandomStringUtils.randomNumeric(3));
        double max = Double.parseDouble(RandomStringUtils.randomNumeric(3));

        while (max < min)
            max = Double.parseDouble(RandomStringUtils.randomNumeric(3));

        Range.Area result = new Range.Area();
        result.setMaximum(max);
        result.setMinimum(min);
        return result;
    }


    public static Role randomRoleGenerator() {
        return Role.findByValue(random.nextInt(3) + 1);
    }


    public static Action randomActionGenerator() {
        return Action.findByValue(random.nextInt(3) + 1);
    }


    public static Names randomNameGenerator() {
        return Names.findByValue(random.nextInt(29)+1);
    }


    public static ContractType randomContractTypeGenerator() {
        return ContractType.findByValue(random.nextInt(3) + 1);
    }


    public static EstateType randomEstateTypeGenerator() {
        return EstateType.findByValue(random.nextInt(4) + 1);
    }


    public static Range.Price randomPriceRangeGenerator() {
        long min = Long.parseLong(RandomStringUtils.randomNumeric(8));
        long max = Long.parseLong(RandomStringUtils.randomNumeric(8));

        while (max < min)
            max = Long.parseLong(RandomStringUtils.randomNumeric(8));

        Range.Price result = new Range.Price();
        result.setMaximum(max);
        result.setMinimum(min);
        return result;
    }

    /********************************
     *
     *      USER INFO GENERATOR
     *
     ********************************/

    public static User userInfoGenerator() {
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

    public static Estate estateInfoGenerator(User owner) { // TODO 값 채워넣기
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

    public static Bid bidInfoGenerator(User user, Action action, Estate estate) {
        return Bid.builder()
                .user(user)
                .estate(estate)
                .action(action)
                .priceRange(randomPriceRangeGenerator()).build();
    }

}
