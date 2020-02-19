package com.sondahum.mamas

import com.sondahum.mamas.bid.domain.Bid
import com.sondahum.mamas.estate.domain.Estate
import com.sondahum.mamas.user.domain.User
import com.sondahum.mamas.estate.domain.ContractType
import com.sondahum.mamas.estate.domain.EstateType
import com.sondahum.mamas.model.Role
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class AbstractMamasSearchTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName())
    private Random random = new Random()

    /******************************
     *
     *      RANDOM INFO GENERATORS
     *
     *******************************/
    @Test
    String randomStringGenerator(int length) {
        return RandomStringUtils.randomAlphanumeric(length)
    }

    @Test
    String randomPhoneNumberGenerator() {
        String middle = RandomStringUtils.randomNumeric(4)
        String last = RandomStringUtils.randomNumeric(4)

        return "010-" + middle + "-" + last
    }

    @Test
    Long randomIdGenerator() {
        return random.nextLong()
    }

    @Test
    Integer randomNumbersGenerator(int length) {
        return random.nextInt(length)
    }

    @Test
    Role randomRoleGenerator() {
        return Role.findByValue(random.nextInt(3) + 1)
    }

    @Test
    Names randomNameGenerator() {
        return Names.findByValue(random.nextInt(29)+1)
    }

    @Test
    ContractType randomContractTypeGenerator() {
        return ContractType.findByValue(random.nextInt(3) + 1)
    }

    @Test
    EstateType randomEstateTypeGenerator() {
        return EstateType.findByValue(random.nextInt(4) + 1)
    }

    @Test
    List<Long> randomPriceRangeGenerator() {
        List<Long> range = []
        Long min = Long.parseLong(RandomStringUtils.randomNumeric(8))
        Long max = Long.parseLong(RandomStringUtils.randomNumeric(8))

        while (max < min)
            max = Long.parseLong(RandomStringUtils.randomNumeric(8))

        range.add(min)
        range.add(max)

        return range
    }

    /********************************
     *
     *      USER INFO GENERATOR
     *
     ********************************/
    List<User> userInfoGenerator(int number) {
        List<User> result = []

        for (int i = 0; i < number; i++) {
            result.add(
                    new User(
                            name: randomNameGenerator().name,
                            phone: randomPhoneNumberGenerator(),
                            role: randomRoleGenerator()
                    )
            )
        }
        return result
    }

    /********************************
     *
     *      ESTATE INFO GENERATOR
     *
     ********************************/
    @Test
    List<Estate> estateInfoGenerator(int number) {
        List<Estate> result = []

        for (int i = 0; i < number; i++) {
            List<Long> randomMarketPrice = randomPriceRangeGenerator()
            List<Long> randomOwnerPrice = randomPriceRangeGenerator()

            result.add(new Estate(
                    address1: randomStringGenerator(8),
                    address2: randomStringGenerator(5),
                    address3: randomStringGenerator(3),
                    area: randomNumbersGenerator(200).toString(),
                    contractType: randomContractTypeGenerator(),
                    estateType: randomEstateTypeGenerator(),
                    marketMinimumPrice: randomMarketPrice[0],
                    marketMaximumPrice: randomMarketPrice[1],
                    ownerMinimumPrice: randomOwnerPrice[0],
                    ownerMaximumPrice: randomOwnerPrice[1],
                    owner: new User(
                            name: randomNameGenerator().name,
                            phone: randomPhoneNumberGenerator(),
                            role: randomRoleGenerator(),
                            bidList: null
                    )
            ))
        }
        return result
    }


    /********************************
     *
     *      BID INFO GENERATOR
     *
     ********************************/
    @Test
    List<Bid> addBidForExistUser(int newUsers, int existUsers, List<User> userList) {
        if (existUsers > userList.size()) {
            logger.error("wrong exist user's bid info number.")
            throw new Exception()
        }
        List<Bid> result = []

        List<Integer> indexes = []
        for (int i = 0; i < existUsers; i++) {
            int num = random.nextInt(userList.size())

            while (indexes.contains(num)) // 중복 방지
                num = random.nextInt(userList.size())

            indexes.add(num)
        } // 여기서 bid 정보 넣을 userList의 index 뽑음

        indexes.each { index ->
            List<Long> randomBidPrice = randomPriceRangeGenerator()
            String randomAction = randomNumbersGenerator(2) % 2 ? "sell" : "buy"
            result.add(new Bid(
                    user: userList[index],
                    action: randomAction,
                    estate: estateInfoGenerator(1).pop(),
                    minimumPrice: randomBidPrice[0],
                    maximumPrice: randomBidPrice[1]
            ))
        }
        for (int i = 0; i < newUsers; i++) {
            String randomAction = randomNumbersGenerator(2) % 2 ? "sell" : "buy"
            List<Long> randomBidPrice = randomPriceRangeGenerator()
            result.add(new Bid(
                    user: userInfoGenerator(1).pop(),
                    action: randomAction,
                    estate: estateInfoGenerator(1).pop(),
                    minimumPrice: randomBidPrice[0],
                    maximumPrice: randomBidPrice[1]
            ))
        }
        return result
    }
}
