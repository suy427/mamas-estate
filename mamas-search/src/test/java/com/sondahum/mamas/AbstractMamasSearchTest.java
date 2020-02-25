package com.sondahum.mamas;

import com.sondahum.mamas.bid.domain.Action;
import com.sondahum.mamas.bid.domain.Bid;
import com.sondahum.mamas.common.model.Address;
import com.sondahum.mamas.common.model.Price;
import com.sondahum.mamas.estate.domain.Estate;
import com.sondahum.mamas.user.domain.User;
import com.sondahum.mamas.estate.domain.ContractType;
import com.sondahum.mamas.estate.domain.EstateType;
import com.sondahum.mamas.common.model.Role;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
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
    public String randomPhoneNumberGenerator() {
        String middle = RandomStringUtils.randomNumeric(4);
        String last = RandomStringUtils.randomNumeric(4);

        return "010-" + middle + "-" + last;
    }

    @Test
    public Long randomIdGenerator() {
        return random.nextLong();
    }

    @Test
    public Integer randomNumbersGenerator(int length) {
        return random.nextInt(length);
    }

    @Test
    public Role randomRoleGenerator() {
        return Role.findByValue(random.nextInt(3) + 1);
    }

    @Test
    public Names randomNameGenerator() {
        return Names.findByValue(random.nextInt(29)+1);
    }

    @Test
    public ContractType randomContractTypeGenerator() {
        return ContractType.findByValue(random.nextInt(3) + 1);
    }

    @Test
    public EstateType randomEstateTypeGenerator() {
        return EstateType.findByValue(random.nextInt(4) + 1);
    }

    @Test
    public List<Long> randomPriceRangeGenerator() {
        List<Long> range = new ArrayList<>();
        Long min = Long.parseLong(RandomStringUtils.randomNumeric(8));
        Long max = Long.parseLong(RandomStringUtils.randomNumeric(8));

        while (max < min)
            max = Long.parseLong(RandomStringUtils.randomNumeric(8));

        range.add(min);
        range.add(max);

        return range;
    }

    /********************************
     *
     *      USER INFO GENERATOR
     *
     ********************************/
    public List<User> userInfoGenerator(int number) {
        List<User> result = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            result.add(User.builder()
                    .name(randomNameGenerator().name)
                    .phone(randomPhoneNumberGenerator())
                    .role(randomRoleGenerator()).build()
            );
        }
        return result;
    }

    /********************************
     *
     *      ESTATE INFO GENERATOR
     *
     ********************************/
    @Test
    public List<Estate> estateInfoGenerator(int number) { // TODO 값 채워넣기
        List<Estate> result = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            List<Long> randomMarketPrice = randomPriceRangeGenerator();
            List<Long> randomOwnerPrice = randomPriceRangeGenerator();

            result.add(Estate.builder()
                    .address(new Address())
                    .area(randomNumbersGenerator(200).toString())
                    .contractType(randomContractTypeGenerator())
                    .estateType(randomEstateTypeGenerator())
                    .marketPriceRange(new Price())
                    .ownerRequirePriceRange(new Price())
                    .owner(User.builder().build()).build()

            );
        }
        return result;
    }


    /********************************
     *
     *      BID INFO GENERATOR
     *
     ********************************/
    @Test
    public List<Bid> addBidForExistUser(int newUsers, int existUsers, List<User> userList) throws Exception {
        if (existUsers > userList.size()) {
            logger.error("wrong exist user's bid info number.");
            throw new Exception();
        }
        List<Bid> result = new ArrayList<>();

        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < existUsers; i++) {
            int num = random.nextInt(userList.size());

            while (indexes.contains(num)) // 중복 방지
                num = random.nextInt(userList.size());

            indexes.add(num);
        } // 여기서 bid 정보 넣을 userList의 index 뽑음

        for (Integer index : indexes) {
            List<Long> randomBidPrice = randomPriceRangeGenerator();
            String randomAction = randomNumbersGenerator(2) % 2 == 0 ? "sell" : "buy";
            result.add(Bid.builder()
                    .user(User.builder().build())
                    .action(Action.BUY)
                    .estate(Estate.builder().build())
                    .priceRange(new Price()).build()
            );
        }

        for (int i = 0; i < newUsers; i++) {
            String randomAction = randomNumbersGenerator(2) % 2  == 0 ? "sell" : "buy";
            List<Long> randomBidPrice = randomPriceRangeGenerator();
            result.add(Bid.builder()
                    .user(User.builder().build())
                    .action(Action.BUY)
                    .estate(Estate.builder().build())
                    .priceRange(new Price()).build()
            );
        }
        return result;
    }
}
