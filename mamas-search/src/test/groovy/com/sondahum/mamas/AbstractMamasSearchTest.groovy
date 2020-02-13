package com.sondahum.mamas

import com.sondahum.mamas.domain.Bid
import com.sondahum.mamas.domain.Estate
import com.sondahum.mamas.domain.User
import com.sondahum.mamas.model.ContractType
import com.sondahum.mamas.model.EstateType
import com.sondahum.mamas.model.Role
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class AbstractMamasSearchTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName())
    private Random random = new Random()

    @Test
    String randomNameGenerator(int length) {
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
    Long randomNumbersGenerator(int length) {
        return random.nextInt(length)
    }

    @Test
    Role randomRoleGenerator() {
        return Role.findByValue(random.nextInt(3) + 1)
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
    List<User> userInfoGenerator(int number) {
        List<User> result = []

        for (int i = 0; i < number; i++) {
            result.add(new User(
//                    id: randomIdGenerator(),
                    name: randomNameGenerator(5),
                    phone: randomPhoneNumberGenerator(),
                    role: randomRoleGenerator(),
                    bidList: null
            ))
        }

        return result
    }

    @Test
    List<Bid> bidInfoGenerator(int number, List<User> userList) {
        List<Bid> result = []
        String randomAction

        List<Integer> indexes = []
        for (int i = 0; i < number; i++) {
            int num = random.nextInt(userList.size())

            while (indexes.contains(num)) // 중복 방지
                num = random.nextInt(userList.size())

            indexes.add(num)
        } // 여기서 bid 정보 넣을 user 선별함.

        indexes.each { index ->
            randomAction = "sell"
            List<String> randomBidPrice = randomPriceRangeGenerator()
            List<String> randomMarketPrice = randomPriceRangeGenerator()
            List<String> randomOwnerPrice = randomPriceRangeGenerator()
            result.add(new Bid(
                    user: userList[index],
                    action: randomAction,
                    estate: new Estate(
                            address1: "",
                            address2: "",
                            address3: "",
                            area: "",
                            contractType: randomContractTypeGenerator(),
                            estateType: randomEstateTypeGenerator(),
                            marketMinimumPrice: randomMarketPrice[0],
                            marketMaximumPrice: randomMarketPrice[1],
                            ownerMinimumPrice: randomOwnerPrice[0],
                            ownerMaximumPrice: randomOwnerPrice[1],
                            sellerId: new User(
                                    name: "james"+randomNameGenerator(3),
                                    phone: randomPhoneNumberGenerator(),
                                    role: randomRoleGenerator()
                            )
                    ),
                    minimumPrice: randomBidPrice[0],
                    maximumPrice: randomBidPrice[1]
            ))
        }
        return result
    }

    @Test
    List<String> randomPriceRangeGenerator() {
        List<String> range = []
        String min = RandomStringUtils.randomNumeric(8)
        String max = RandomStringUtils.randomNumeric(8)

        while (Integer.parseInt(max) < Integer.parseInt(min))
            max = RandomStringUtils.randomNumeric(8)

        range.add(min)
        range.add(max)

        return range
    }

}
