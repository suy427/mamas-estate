package com.sondahum.mamas

import com.sondahum.mamas.domain.Bid
import com.sondahum.mamas.domain.User
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

        return "010-"+middle+"-"+last
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
    Role randomEnumTypeGenerator() {
        return Role.findByValue(random.nextInt(3)+1)
    }

    @Test
    List<User> userInfoGenerator(int number) {
        List<User> result = []

        for (int i = 0; i < number; i++) {
            result.add(new User(
//                    id: randomIdGenerator(),
                    name: randomNameGenerator(5),
                    phone: randomPhoneNumberGenerator(),
                    role: randomEnumTypeGenerator(),
                    bidList: null
            ))
        }

        return result
    }

    @Test
    List<Bid> bidInfoGenerator(int number, List<User> userList) {
        List<Bid> result = []
        String randomAction = random.nextInt(2) == 1 ? "sell" : "buy"

        for(int i = 0; i < number; i++) {
            result.add(new Bid(
                    user: randomIdGenerator(),
                    action: randomAction
            ))
        }
        return result
    }

    @Test
    List<String> randomPriceRangeGenerator() {
        List<String> range = []
        String min = RandomStringUtils.randomNumeric(8)
        String max = RandomStringUtils.randomNumeric(8)

        while(Integer.parseInt(max) < Integer.parseInt(min))
            max = RandomStringUtils.randomNumeric(8)

        range.add(min)
        range.add(max)

        return range
    }

}
