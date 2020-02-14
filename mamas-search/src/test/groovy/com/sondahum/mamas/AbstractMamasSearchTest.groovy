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
                            name: randomStringGenerator(5),
                            phone: randomPhoneNumberGenerator(),
                            role: randomRoleGenerator(),
                            bidList: null
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
            List<String> randomMarketPrice = randomPriceRangeGenerator()
            List<String> randomOwnerPrice = randomPriceRangeGenerator()

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

            result.add(new Bid(
                    user: userList[index],
                    action: randomAction,
                    estate: estateInfoGenerator(1).pop(),
                    minimumPrice: randomBidPrice[0],
                    maximumPrice: randomBidPrice[1]
            ))
        }
        return result
    }

    enum Names {
        JAMES(1, "JAMES"), BETTY(2, "BETTY"), CLARK(3, "CLARK"),
        JOHN(4,"JOHN"), KIM(5,"KIM"), PARK(6,"PARK"),
        MATHEW(7,"MATHEW"), NATHAN(8,"MATHEW"), EVA(9,"EVA"),
        HAPPY(10,"HAPPY"), KANE(11, "KANE"), FINCHER(12,"FINCHER"),
        SIMON(13, "SIMON"), PAUL(14, "PAUL"), MICHEL(15, "MICHEL"),
        JANE(16, "JANE"), CHOI(17, "CHOI"), SON(18, "SON"),
        JANG(19, "JANG"), MARK(20, "MARK"), RONNY(21,"RONNY"),
        EMMA(22,"EMMA"), DANIEL(23,"DANIEL"), HARRY(24,"HARRY"),
        PRODO(25,"PRODO"), RYAN(26,"RYAN"), GEORGE(27,"GEORGE"),
        QUEUE(28,"QUEUE"), WILLY(29,"WILLY")

        private final int value
        private final String name
        private static final LinkedHashMap<Integer, Names> valueMap = [:]
        private static final LinkedHashMap<String, Names> nameMap = [:]

        static {
            for (Names role : values()) {
                valueMap.put(role.value, role)
                nameMap.put(role.name, role)
            }
        }

        Names(int value, String name) {
            this.value = value
            this.name = name
        }

        static Names findByValue(int value) {
            return valueMap[value]
        }

        static Names findByName(String name) {
            return nameMap[name]
        }
    }

}
