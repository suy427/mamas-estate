package com.sondahum.mamas;

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

        final int value
        final String name
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